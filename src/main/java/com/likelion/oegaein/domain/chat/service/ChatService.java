package com.likelion.oegaein.domain.chat.service;

import com.likelion.oegaein.domain.chat.dto.*;
import com.likelion.oegaein.domain.chat.entity.ChatRoom;
import com.likelion.oegaein.domain.chat.entity.ChatRoomMember;
import com.likelion.oegaein.domain.chat.entity.Message;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.repository.MatchingPostRepository;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.chat.repository.ChatRoomMemberRepository;
import com.likelion.oegaein.domain.chat.repository.ChatRoomRepository;
import com.likelion.oegaein.domain.chat.repository.MessageRepository;
import com.likelion.oegaein.domain.chat.repository.query.ChatRoomMemberQueryRepository;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {
    // di
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomMemberQueryRepository chatRoomMemberQueryRepository;
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final MatchingPostRepository matchingPostRepository;

    private final MessageService messageService;

    // constant
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";
    private final String NOT_FOUND_MATCHING_POST_ERR_MSG = "찾을 수 없는 매칭글입니다.";
    private final String CHAT_ROOM_NAME_POSTFIX = " 행성방";

    public FindChatRoomsResponse findChatRooms(){
        // find login user
        Long authenticatedMemberId = 2L; // 임시 인증 유저 ID
        Member authenticatedMember = memberRepository.findById(authenticatedMemberId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: member")); // 에러 메시지 국제화 필요
        // find ChatRoomMembers
        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findByMember(authenticatedMember);
        // create FindChatRoomsData
        List<FindChatRoomsData> findChatRoomsDatas = chatRoomMembers.stream()
                .map((chatRoomMember) -> {
                    // local var
                    ChatRoom chatRoom = chatRoomMember.getChatRoom();
                    String roomId = chatRoom.getRoomId();
                    String roomName = chatRoom.getRoomName();
                    int memberCount = chatRoom.getMemberCount();
                    LocalDateTime disconnectedAt = chatRoomMember.getDisconnectedAt();
                    // find unread messages
                    List<Message> unReadMessages = messageRepository.findByRoomIdAndDateAfterOrderByDateAsc(roomId, disconnectedAt);
                    // find all of messages
                    FindMessagesResponse response = messageService.getMessages(roomId);
                    List<FindMessageData> allOfMessages = response.getData();

                    if(allOfMessages.isEmpty()){
                        return FindChatRoomsData.builder()
                                .roomName(roomName)
                                .memberCount(memberCount)
                                .unReadMessageCount(unReadMessages.size())
                                .build();
                    }
                    FindMessageData lastMessage = allOfMessages.get(allOfMessages.size()-1);
                    // create response
                    return FindChatRoomsData.builder()
                            .roomName(roomName)
                            .memberCount(memberCount)
                            .unReadMessageCount(unReadMessages.size())
                            .lastMessageContent(lastMessage.getMessage())
                            .lastMessageDate(lastMessage.getDate())
                            .build();
                }).toList();
        return new FindChatRoomsResponse(findChatRoomsDatas.size(), findChatRoomsDatas);
    }

    @Transactional
    public CreateChatRoomResponse createChatRoom(CreateChatRoomData dto){
        // find chat member & matchingPost
        Member authenticatedMember = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        MatchingPost matchingPost = matchingPostRepository.findById(dto.getMatchingPostId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MATCHING_POST_ERR_MSG));
        // create chat room
        String chatRoomName = generateChatRoomName(matchingPost.getTitle());
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(chatRoomName)
                .matchingPost(matchingPost)
                .build();
        chatRoomRepository.save(chatRoom);
        return new CreateChatRoomResponse(chatRoom.getRoomId());
    }

    @Transactional
    public DeleteChatRoomResponse removeOneToOneChatRoom(String roomId){
        // find chat member
        Long authenticatedMemberId = 1L;
        Member authenticatedMember = memberRepository.findById(authenticatedMemberId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: member"));
        // find ChatRoom
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: chatRoom"));
        // find ChatRoomMember
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMember(chatRoom, authenticatedMember)
                        .orElseThrow(() -> new EntityNotFoundException("Not Found: chatRoomMember"));
        chatRoomMemberRepository.delete(chatRoomMember);
        chatRoom.downMemberCount();
        if(chatRoom.getMemberCount() == 0){ // 모두 방에서 나갔는지 확인
            chatRoomRepository.delete(chatRoom);
        }
        return new DeleteChatRoomResponse(roomId, authenticatedMemberId);
    }

    @Transactional
    public void updateDisconnectedAt(UpdateDisconnectedAtRequest dto){
        // find ChatRoomMember
        ChatRoomMember chatRoomMember = chatRoomMemberQueryRepository.findByRoomIdAndName(
                dto.getRoomId(), dto.getName()
        ).orElseThrow(() -> new EntityNotFoundException("Not Found: chatRoomMember"));
        // update disconnectedAt
        chatRoomMember.updateDisconnectedAt(LocalDateTime.now());
    }

    // custom method
    private String generateChatRoomName(String matchingPostTitle){
        int titleLength = matchingPostTitle.length();
        if(titleLength > 10){
            String slicedTitle = matchingPostTitle.substring(0, 11);
            return slicedTitle + "..." + CHAT_ROOM_NAME_POSTFIX;
        }
        return matchingPostTitle + CHAT_ROOM_NAME_POSTFIX;
    }
}
