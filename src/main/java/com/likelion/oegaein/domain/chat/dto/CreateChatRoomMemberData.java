package com.likelion.oegaein.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateChatRoomMemberData {
    private Long chatRoomId;
    private Long memberId;
    public static CreateChatRoomMemberData toCreateChatRoomMemberData(CreateChatRoomMemberRequest dto){
        return CreateChatRoomMemberData.builder()
                .chatRoomId(dto.getChatRoomId())
                .memberId(dto.getMemberId())
                .build();
    }
}