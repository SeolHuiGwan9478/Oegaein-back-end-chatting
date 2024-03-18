package com.likelion.oegaein.dto.chat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindChatRoomsData {
    private String chatRoomName; // 채팅방 이름
    private Long memberCount; // 참여자 수
    private int unReadMessageCount; // 읽지 않은 메세지 수
    private String recentMessage; // 마지막 채팅 내용
}
