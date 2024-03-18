package com.likelion.oegaein.dto.chat;

import com.likelion.oegaein.dto.global.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindChatRoomsResponse implements ResponseDto {
    private int count;
    private List<FindChatRoomsData> data;
}
