package com.likelion.oegaein.dto.chat;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateChatRoomData {
    private Long matchingPostId;
    private String matchingPostTitle;
    private List<Long> opponentMemberIds;
    public static CreateChatRoomData toCreateChatRoomData(CreateChatRoomRequest dto){
        return CreateChatRoomData.builder()
                .matchingPostId(dto.getMatchingPostId())
                .matchingPostTitle(dto.getMatchingPostTitle())
                .opponentMemberIds(dto.getOpponentMemberIds())
                .build();
    }
}
