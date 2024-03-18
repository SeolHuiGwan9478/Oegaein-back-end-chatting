package com.likelion.oegaein.dto.chat;

import lombok.Getter;

@Getter
public class CreateOneToOneChatRoomRequest {
    private Long opponentMemberId; // 상대방 ID
}
