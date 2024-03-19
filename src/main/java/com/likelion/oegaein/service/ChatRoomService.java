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
import com.likelion.oegaein.repository.member.query.MemberQueryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EmptyStackException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomMemberQueryRepository chatRoomMemberQueryRepository;
    private final MessageRepository messageRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;

    private final String CHAT_ROOM_NAME_SEP = ",";
    private final Long ONE_TO_ONE_CHAT_ROOM_MEMBER_COUNT = 2L;

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
                    ChatRoom chatRoom = chatRoomMember.getChatRoom();
                    String roomId = chatRoom.getRoomId();
                    String roomName = chatRoom.getRoomName();
                    Long memberCount = chatRoom.getMemberCount();
                    LocalDateTime disconnectedAt = chatRoomMember.getDisconnectedAt();
                    List<Message> unReadMessages = messageRepository.findByRoomIdAndDateAfter(roomId, disconnectedAt);
                    FindChatRoomsData findChatRoomsData = FindChatRoomsData.builder()
                            .roomName(roomName)
                            .memberCount(memberCount)
                            .unReadMessageCount(unReadMessages.size())
                            .build();
                    return findChatRoomsData;
                }).toList();
        return new FindChatRoomsResponse(findChatRoomsDatas.size(), findChatRoomsDatas);
    }

    @Transactional
    public CreateOneToOneChatRoomResponse createOneToOneChatRoom(CreateOneToOneChatRoomData dto){
        // find chat members
        Long authenticatedMemberId = 1L; // 임시 인증 유저 ID
        Long opponentMemberId = dto.getOpponentMemberId(); // 상대방 ID
        Member authenticatedMember = memberRepository.findById(authenticatedMemberId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: member")); // 에러 메시지 국제화 필요
        Member opponentMember = memberRepository.findById(opponentMemberId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: member"));
        List<Member> chatMembers = List.of(authenticatedMember, opponentMember);
        // create chat room
        String chatRoomName = opponentMember.getProfile().getName();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(chatRoomName)
                .memberCount(ONE_TO_ONE_CHAT_ROOM_MEMBER_COUNT)
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
        return new CreateOneToOneChatRoomResponse(chatRoom.getRoomId());
    }

    @Transactional
    public DeleteOneToOneChatRoomResponse removeOneToOneChatRoom(String roomId){
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
        return new DeleteOneToOneChatRoomResponse(roomId, authenticatedMemberId);
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
