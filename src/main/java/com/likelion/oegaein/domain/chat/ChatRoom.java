package com.likelion.oegaein.domain.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    @Id @GeneratedValue
    private Long id;
    private String roomId; // 채팅방 ID
    private String roomName; // 채팅방 이름
    private Long memberCount; // 채팅방 인원
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "chatRoom")
    @Builder.Default
    private List<ChatRoomMember> chatRoomMembers = new ArrayList<>();

    public void upMemberCount(){
        this.memberCount++;
    }

    public void downMemberCount(){
        this.memberCount--;
    }
}