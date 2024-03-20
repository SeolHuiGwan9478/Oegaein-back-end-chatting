package com.likelion.oegaein.service;

import com.likelion.oegaein.domain.chat.ChatRoom;
import com.likelion.oegaein.domain.chat.ChatRoomMember;
import com.likelion.oegaein.domain.chat.Message;
import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.dto.chat.*;
import com.likelion.oegaein.repository.chat.ChatRoomMemberRepository;
import com.likelion.oegaein.repository.chat.ChatRoomRepository;
import com.likelion.oegaein.repository.chat.MessageRepository;
import com.likelion.oegaein.repository.chat.query.ChatRoomMemberQueryRepository;
import com.likelion.oegaein.repository.member.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomMemberQueryRepository chatRoomMemberQueryRepository;
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    private final MessageService messageService;

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
        // find chat members
        Long authenticatedMemberId = 1L; // 임시 인증 유저 ID
        List<Long> opponentMemberIds = dto.getOpponentMemberIds(); // 상대방 IDs
        Member authenticatedMember = memberRepository.findById(authenticatedMemberId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: member")); // 에러 메시지 국제화 필요
        List<Member> opponentMembers = opponentMemberIds.stream()
                .map((opponentMemberId) -> memberRepository.findById(opponentMemberId)
                        .orElseThrow(() -> new EntityNotFoundException("Not Found: member"))).toList();
        List<Member> chatMembers = new ArrayList<>();
        chatMembers.add(authenticatedMember);
        chatMembers.addAll(opponentMembers);

        // create chat room
        String chatRoomName = dto.getMatchingPostTitle() + CHAT_ROOM_NAME_POSTFIX;
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(chatRoomName)
                .memberCount(chatMembers.size())
                .build();
        chatRoomRepository.save(chatRoom);
        // create chat room member
        chatMembers.forEach((chatMember) -> {
            ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                    .member(chatMember)
                    .chatRoom(chatRoom)
                    .disconnectedAt(LocalDateTime.now())
                    .build();
            chatRoomMemberRepository.save(chatRoomMember);
        });
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
}
