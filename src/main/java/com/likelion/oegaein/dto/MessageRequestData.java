package com.likelion.oegaein.dto;

import com.likelion.oegaein.domain.MessageStatus;
import lombok.Data;

@Data
public class MessageRequestData {
    private String chattingRoomId; // 채팅방 ID
    private String senderName; // 보낸 회원 이름
    private String message; // 메시지 내용
    private MessageStatus messageStatus; // 메시지 타입

    public static MessageRequestData toMessageRequestData(MessageRequest dto){
        MessageRequestData messageRequestData = new MessageRequestData();
        messageRequestData.setChattingRoomId(dto.getChattingRoomId());
        messageRequestData.setSenderName(dto.getSenderName());
        messageRequestData.setMessage(dto.getMessage());
        messageRequestData.setMessageStatus(dto.getMessageStatus());
        return messageRequestData;
    }
}
