package com.likelion.oegaein.domain.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    private String chatRoomId; // 채팅방 ID
    private String chatRoomName; // 채팅방 이름
    private Long memberCount; // 채팅방 인원
    @OneToMany(orphanRemoval = true)
    @Builder.Default
    private List<ChatRoomMember> memberList = new ArrayList<>(); // 참가자 목록
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void upMemberCount(){
        this.memberCount++;
    }

    public void downMemberCount(){
        this.memberCount--;
    }
}