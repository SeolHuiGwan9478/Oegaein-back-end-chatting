package com.likelion.oegaein.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateChatRoomMemberData {
    private Long chatRoomId;
    public static CreateChatRoomMemberData toCreateChatRoomMemberData(CreateChatRoomMemberRequest dto){
        return CreateChatRoomMemberData.builder()
                .chatRoomId(dto.getChatRoomId())
                .build();
    }
}