package com.likelion.oegaein.domain.chat.dto;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindUnreadMessageResponse implements ResponseDto {
    private int totalUnreadMessageCount;

    public void upTotalUnreadMessageCount(int upCount){
        this.totalUnreadMessageCount += upCount;
    }
}