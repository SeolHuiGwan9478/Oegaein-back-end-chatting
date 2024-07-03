package com.likelion.oegaein.domain.chat.dto;

import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindMessagesResponse implements ResponseDto {
    private Long matchingPostId;
    private MatchingStatus matchingStatus;
    private String roomName;
    private int memberCount;
    private List<FindMessageData> data;
}