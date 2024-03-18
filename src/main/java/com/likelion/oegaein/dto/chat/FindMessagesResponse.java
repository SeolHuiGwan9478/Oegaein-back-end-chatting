package com.likelion.oegaein.dto.chat;

import com.likelion.oegaein.dto.global.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindMessagesResponse implements ResponseDto {
    private List<FindMessageData> data;
}
