package com.likelion.oegaein.domain.chat.service;

import com.likelion.oegaein.domain.chat.dto.CreateChatRoomMemberData;
import com.likelion.oegaein.domain.chat.dto.CreateChatRoomMemberResponse;
import com.likelion.oegaein.domain.chat.dto.UpdateDisconnectedAtRequest;
import com.likelion.oegaein.domain.chat.entity.ChatRoom;
import com.likelion.oegaein.domain.chat.entity.ChatRoomMember;
import com.likelion.oegaein.domain.chat.repository.ChatRoomMemberRepository;
import com.likelion.oegaein.domain.chat.repository.ChatRoomRepository;
import com.likelion.oegaein.domain.chat.repository.query.ChatRoomMemberQueryRepository;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomMemberService {
    // DI
    private final ChatRoomMemberQueryRepository chatRoomMemberQueryRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    // constant
    private final String NOT_FOUND_CHAT_ROOM_MEMBER_ERR_MSG = "찾을 수 없는 채팅방 참가자입니다.";
    private final String NOT_FOUND_CHAT_ROOM_ERR_MSG = "찾을 수 없는 채팅방입니다.";
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";

    @Transactional
    public CreateChatRoomMemberResponse createChatRoomMember(CreateChatRoomMemberData dto, Authentication authentication){
        ChatRoom findChatRoom = chatRoomRepository.findById(dto.getChatRoomId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_CHAT_ROOM_ERR_MSG));
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        ChatRoomMember newChatRoomMember = ChatRoomMember.builder()
                .member(authenticatedMember)
                .chatRoom(findChatRoom)
                .disconnectedAt(LocalDateTime.now())
                .build();
        chatRoomMemberRepository.save(newChatRoomMember);
        findChatRoom.upMemberCount();
        return new CreateChatRoomMemberResponse(newChatRoomMember.getId());
    }

    @Transactional
    public void updateDisconnectedAt(UpdateDisconnectedAtRequest dto){
        // find ChatRoomMember
        ChatRoomMember chatRoomMember = chatRoomMemberQueryRepository.findByRoomIdAndName(
                dto.getRoomId(), dto.getName()
        ).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_CHAT_ROOM_MEMBER_ERR_MSG));
        // update disconnectedAt
        chatRoomMember.updateDisconnectedAt(LocalDateTime.now());
    }
}
