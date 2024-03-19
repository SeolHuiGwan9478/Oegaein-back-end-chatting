package com.likelion.oegaein.dto.chat;

import com.likelion.oegaein.dto.global.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOneToOneChatRoomResponse implements ResponseDto {
    private String roomId;
}
