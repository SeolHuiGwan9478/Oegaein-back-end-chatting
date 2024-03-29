package com.likelion.oegaein.dto.chat;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FindChatRoomsData {
    private String roomName; // 채팅방 이름
    private int memberCount; // 참여자 수
    private int unReadMessageCount; // 읽지 않은 메세지 수
    private String lastMessageContent; // 마지막 채팅 내용
    private LocalDateTime lastMessageDate; // 마지막 채팅 보낸 시간
}
