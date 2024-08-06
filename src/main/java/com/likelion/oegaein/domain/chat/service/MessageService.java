package com.likelion.oegaein.domain.chat.service;

import com.likelion.oegaein.domain.chat.entity.ChatRoom;
import com.likelion.oegaein.domain.chat.entity.ChatRoomMember;
import com.likelion.oegaein.domain.chat.dto.FindMessageData;
import com.likelion.oegaein.domain.chat.dto.FindMessagesResponse;
import com.likelion.oegaein.domain.chat.dto.MessageRequestData;
import com.likelion.oegaein.domain.chat.dto.MessageResponse;
import com.likelion.oegaein.domain.chat.entity.Message;
import com.likelion.oegaein.domain.chat.entity.MessageStatus;
import com.likelion.oegaein.domain.chat.exception.MessageException;
import com.likelion.oegaein.domain.chat.repository.ChatRoomMemberRepository;
import com.likelion.oegaein.domain.chat.repository.ChatRoomRepository;
import com.likelion.oegaein.domain.chat.repository.MessageRepository;
import com.likelion.oegaein.domain.chat.repository.RedisRepository;
import com.likelion.oegaein.domain.chat.repository.query.ChatRoomMemberQueryRepository;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageService {
    // DI
    private final MessageRepository messageRepository;
    private final RedisRepository redisRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomMemberQueryRepository chatRoomMemberQueryRepository;
    private final MemberRepository memberRepository;
    // constant
    private final String CHAT_LEAVE_MSG = "님이 퇴장하였습니다.";
    private final String MESSAGE_HEADER_ERR_MSG = "메세지 헤더를 찾을 수 없습니다.";
    private final String NOT_FOUND_CHAT_ROOM_MEMBER_ERR_MSG = "찾을 수 없는 채팅방 참가자입니다.";
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";
    private final String NOT_FOUND_CHAT_ROOM_ERR_MSG = "찾을 수 없는 채팅방입니다.";
    private final String MESSAGE_HEADER_SENDER_ID_KEY = "senderId";
    private final String MESSAGE_HEADER_NAME_KEY = "name";
    private final String MESSAGE_HEADER_ROOM_ID_KEY = "roomId";
    private final String MESSAGE_HEADER_PHOTO_URL_KEY = "photoUrl";
    private final int MAX_CACHE_SIZE_EACH_ROOM = 100;

    // save chatting content
    public MessageResponse saveMessage(MessageRequestData dto, StompHeaderAccessor accessor){
        // get headers
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if(sessionAttributes == null){
            throw new MessageException(MESSAGE_HEADER_ERR_MSG);
        }
        Long senderId = (Long) sessionAttributes.get(MESSAGE_HEADER_SENDER_ID_KEY);
        String senderName = (String) sessionAttributes.get(MESSAGE_HEADER_NAME_KEY);
        String roomId = (String) sessionAttributes.get(MESSAGE_HEADER_ROOM_ID_KEY);
        String photoUrl = (String) sessionAttributes.get(MESSAGE_HEADER_PHOTO_URL_KEY);
        Message message = Message.builder()
                .roomId(roomId)
                .senderId(senderId)
                .senderName(senderName)
                .photoUrl(photoUrl)
                .message(dto.getMessage())
                .messageStatus(dto.getMessageStatus())
                .date(LocalDateTime.now())
                .build(); // setting message
        if(!redisRepository.containsKey(roomId) || redisRepository.get(roomId).isEmpty()){
            List<Message> findMessages = getMessagesInDb(roomId);
            // data in db
            Queue<Message> q = new LinkedList<>(findMessages);
            redisRepository.put(roomId, q);
        }
        Queue<Message> q = redisRepository.get(roomId);
        q.add(message);
        redisRepository.put(roomId, q);
        return new MessageResponse(message);
    }

    public MessageResponse saveCreatedMessage(Message message){
        String roomId = message.getRoomId();
        if(!redisRepository.containsKey(roomId) || redisRepository.get(roomId).isEmpty()){
            List<Message> findMessages = getMessagesInDb(roomId);
            // data in db
            Queue<Message> q = new LinkedList<>(findMessages);
            redisRepository.put(roomId, q);
        }
        Queue<Message> q = redisRepository.get(roomId);
        q.add(message);
        redisRepository.put(roomId, q);
        return new MessageResponse(message);
    }

    // get message list
    public FindMessagesResponse getMessages(String roomId, Authentication authentication){
        List<Message> messages; // messages
        Member member = memberRepository.findByEmail(authentication.getName()).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        // look aside pattern
        if(!redisRepository.containsKey(roomId) || redisRepository.get(roomId).isEmpty()){ // cache miss
            List<Message> findMessages = getMessagesInDb(roomId);
            // data in db
            Queue<Message> q = new LinkedList<>(findMessages);
            redisRepository.put(roomId, q);
            messages = findMessages;
        }else{ // cache hit
            messages = getMessagesInCache(roomId);
        }
        ChatRoom findChatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_CHAT_ROOM_ERR_MSG));
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMember(findChatRoom, member)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_CHAT_ROOM_MEMBER_ERR_MSG));
        String roomName = findChatRoom.getRoomName();
        int memberCount = findChatRoom.getMemberCount();
        MatchingPost matchingPost = findChatRoom.getMatchingPost();
        Long matchingPostId = matchingPost.getId();
        MatchingStatus matchingStatus = matchingPost.getMatchingStatus();
        // split 100 numbers of messages
        if(messages.size() > MAX_CACHE_SIZE_EACH_ROOM){
            messages = new ArrayList<>(messages.subList(messages.size()-MAX_CACHE_SIZE_EACH_ROOM, messages.size()));
        }
        // to FindMessageData
        List<FindMessageData> dto = new ArrayList<>();
        for (Message message:messages){
            LocalDateTime messageDate = message.getDate();
            LocalDateTime createdAt = chatRoomMember.getCreatedAt();
            if(messageDate.isAfter(createdAt)) dto.add(FindMessageData.toFindMessageData(message));
        }
        return new FindMessagesResponse(matchingPost.getAuthor().getId(), matchingPostId, matchingStatus, roomName, memberCount, dto);
    }

    // write back pattern
    private void commitMessageCache(Queue<Message> messageQueue){
        for(int i = 0;i < MAX_CACHE_SIZE_EACH_ROOM;i++){
            Message message = messageQueue.poll();
            messageRepository.save(message);
        }
    }

    // get messages in mongoDB
    private List<Message> getMessagesInDb(String roomId){
        List<Message> messages = messageRepository.findTop100ByRoomIdOrderByDateDesc(roomId);
        return messages.stream().sorted().toList();
    }

    // get messages in redis
    private List<Message> getMessagesInCache(String roomId){
        return redisRepository.get(roomId).stream().toList();
    }
}
