package com.likelion.oegaein.dto.chat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateOneToOneChatRoomData {
    private Long opponentMemberId;
    public static CreateOneToOneChatRoomData toCreateChatRoomData(CreateOneToOneChatRoomRequest dto){
        return CreateOneToOneChatRoomData.builder()
                .opponentMemberId(dto.getOpponentMemberId())
                .build();
    }
}
