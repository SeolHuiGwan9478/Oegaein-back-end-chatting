package com.likelion.oegaein.domain.chat.dto;

import com.likelion.oegaein.domain.chat.entity.Message;
import com.likelion.oegaein.domain.chat.entity.MessageStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindMessageData {
    private String roomId; // 채팅방 ID
    private String senderName; // 보낸 회원 이름
    private String message; // 메시지 내용
    private MessageStatus messageStatus; // 메시지 타입
    private LocalDateTime date; // 메시지 발신 날짜

    public static FindMessageData toFindMessageData(Message message){
        FindMessageData newMessageData = new FindMessageData();
        newMessageData.setRoomId(message.getRoomId());
        newMessageData.setSenderName(message.getSenderName());
        newMessageData.setMessage(message.getMessage());
        newMessageData.setMessageStatus(message.getMessageStatus());
        newMessageData.setDate(message.getDate());
        return newMessageData;
    }
}
