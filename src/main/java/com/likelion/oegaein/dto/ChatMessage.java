package com.likelion.oegaein.dto;

import lombok.Data;

@Data
public class ChatMessage {
    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;
}
