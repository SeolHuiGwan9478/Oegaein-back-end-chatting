package com.likelion.oegaein.domain.chat.dto;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindMessagesResponse implements ResponseDto {
    private List<FindMessageData> data;
}
