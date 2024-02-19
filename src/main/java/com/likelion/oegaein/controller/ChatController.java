package com.likelion.oegaein.controller;

import com.likelion.oegaein.domain.MessageStatus;
import com.likelion.oegaein.dto.MessageRequest;
import com.likelion.oegaein.dto.MessageRequestData;
import com.likelion.oegaein.dto.MessageResponse;
import com.likelion.oegaein.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    // DI
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ChatService chatService;
    // constant
    private final String CHAT_ROOM_JOIN_MSG = "님이 입장하였습니다.";
    @MessageMapping("/message")
    private String sendMessage(@Payload MessageRequest message){
        if(message.getMessageStatus().equals(MessageStatus.JOIN)){ // 채팅방 입장
            log.info("Send Enter Message");
            message.setMessage(message.getSenderName() + CHAT_ROOM_JOIN_MSG);
            // DB 저장 로직
        }else if(message.getMessageStatus().equals(MessageStatus.MESSAGE)){
            log.info("Send Normal Message");
            MessageResponse response = chatService.saveMessage(MessageRequestData.toMessageRequestData(message));
        }
        return "test";
    }
}
