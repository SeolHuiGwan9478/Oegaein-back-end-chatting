package com.likelion.oegaein.domain.chat.dto;

import com.likelion.oegaein.domain.chat.entity.Message;
import com.likelion.oegaein.domain.chat.entity.MessageStatus;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MessageResponse implements ResponseDto {
    private String roomId; // 채팅방 ID
    private String senderName; // 보낸 회원 이름
    private String message; // 메시지 내용
    private MessageStatus messageStatus; // 메시지 타입
    private LocalDateTime date; // 메시지 발신 날짜

    public MessageResponse(Message message){
        this.roomId = message.getRoomId();
        this.senderName = message.getSenderName();
        this.message = message.getMessage();
        this.messageStatus = message.getMessageStatus();
        this.date = message.getDate();
    }
}
