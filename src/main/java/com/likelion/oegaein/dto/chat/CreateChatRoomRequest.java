package com.likelion.oegaein.dto.chat;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateChatRoomRequest {
    private Long matchingPostId;
    private String matchingPostTitle;
    private List<Long> opponentMemberIds; // 상대방 ID
}
