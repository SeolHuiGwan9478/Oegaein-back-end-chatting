package com.likelion.oegaein.dto.chat;

import com.likelion.oegaein.domain.chat.MessageStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageRequest {
    private String message; // 메시지 내용
    private MessageStatus messageStatus; // 메시지 타입
    private LocalDateTime createdAt; // 메시지 발송 시간
}
