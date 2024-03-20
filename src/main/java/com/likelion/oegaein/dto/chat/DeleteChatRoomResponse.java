package com.likelion.oegaein.dto.chat;

import com.likelion.oegaein.dto.global.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteChatRoomResponse implements ResponseDto {
    private String roomId;
    private Long memberId;
}
