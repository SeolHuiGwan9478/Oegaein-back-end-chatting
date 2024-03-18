package com.likelion.oegaein.dto.chat;

import com.likelion.oegaein.domain.chat.MessageStatus;
import com.likelion.oegaein.dto.global.ResponseDto;

import java.time.LocalDateTime;

public class FindMessageResponse implements ResponseDto {
    private String roomId; // 채팅방 ID
    private String senderName; // 보낸 회원 이름
    private String message; // 메시지 내용
    private MessageStatus messageStatus; // 메시지 타입
    private LocalDateTime date; // 메시지 발신 날짜
}
