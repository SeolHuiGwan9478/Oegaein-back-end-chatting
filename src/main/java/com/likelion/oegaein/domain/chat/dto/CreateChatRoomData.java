package com.likelion.oegaein.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateChatRoomData {
    private Long matchingPostId;
    private Long memberId;
    public static CreateChatRoomData toCreateChatRoomData(CreateChatRoomRequest dto){
        return CreateChatRoomData.builder()
                .matchingPostId(dto.getMatchingPostId())
                .memberId(dto.getMemberId())
                .build();
    }
}
