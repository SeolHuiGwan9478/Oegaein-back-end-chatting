package com.likelion.oegaein.domain.chat.dto;

import com.likelion.oegaein.domain.chat.entity.MessageStatus;
import lombok.Data;

@Data
public class MessageRequestData {
    private String message; // 메시지 내용
    private MessageStatus messageStatus; // 메시지 타입

    public static MessageRequestData toMessageRequestData(MessageRequest dto){
        MessageRequestData messageRequestData = new MessageRequestData();
        messageRequestData.setMessage(dto.getMessage());
        messageRequestData.setMessageStatus(dto.getMessageStatus());
        return messageRequestData;
    }
}
