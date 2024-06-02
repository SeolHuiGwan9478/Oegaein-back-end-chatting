package com.likelion.oegaein.global.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class ErrorResponseDto {
    private Map<String, String> errorMessages;
}