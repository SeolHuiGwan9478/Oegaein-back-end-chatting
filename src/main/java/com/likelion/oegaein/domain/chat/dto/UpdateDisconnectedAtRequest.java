package com.likelion.oegaein.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UpdateDisconnectedAtRequest {
    private String roomId;
    private String name;
}
