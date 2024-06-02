package com.likelion.oegaein.domain.chat.entity;

import com.likelion.oegaein.domain.member.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoomMember {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime disconnectedAt; // 소켓 연결 종료 시간

    public void updateDisconnectedAt(LocalDateTime disconnectedAt){
        this.disconnectedAt = disconnectedAt;
    }
}