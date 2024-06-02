package com.likelion.oegaein.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateChatRoomData {
    private Long matchingPostId;
    public static CreateChatRoomData toCreateChatRoomData(CreateChatRoomRequest dto){
        return CreateChatRoomData.builder()
                .matchingPostId(dto.getMatchingPostId())
                .build();
    }
}
