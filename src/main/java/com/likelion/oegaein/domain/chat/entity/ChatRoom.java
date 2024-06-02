package com.likelion.oegaein.domain.chat.entity;

import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId; // 채팅방 ID

    private String roomName; // 채팅방 이름

    private int memberCount; // 참가자 수

    @OneToOne(fetch = FetchType.LAZY)
    private MatchingPost matchingPost;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<ChatRoomMember> chatRoomMembers = new ArrayList<>();


    public void downMemberCount() {
        this.memberCount -= 1;
    }
    public void upMemberCount() {this.memberCount += 1;}s
}