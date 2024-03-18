package com.likelion.oegaein.repository.chat;

import com.likelion.oegaein.domain.chat.ChatRoom;
import com.likelion.oegaein.domain.chat.ChatRoomMember;
import com.likelion.oegaein.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    Optional<ChatRoomMember> findByChatRoomAndMember(ChatRoom chatRoom, Member member);
    List<ChatRoomMember> findByMember(Member member);
}
