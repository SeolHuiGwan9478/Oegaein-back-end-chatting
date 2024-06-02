package com.likelion.oegaein.domain.chat.repository;

import com.likelion.oegaein.domain.chat.entity.ChatRoom;
import com.likelion.oegaein.domain.chat.entity.ChatRoomMember;
import com.likelion.oegaein.domain.member.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    Optional<ChatRoomMember> findByChatRoomAndMember(ChatRoom chatRoom, Member member);
    List<ChatRoomMember> findByMember(Member member);
    List<ChatRoomMember> findByChatRoom(ChatRoom chatRoom);
}
