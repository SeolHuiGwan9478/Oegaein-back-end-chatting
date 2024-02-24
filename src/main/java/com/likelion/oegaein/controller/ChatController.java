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
    private final String CHAT_SUB_PATH = "/sub/chatroom/";

    @MessageMapping("/message")
    private void sendMessage(@Payload MessageRequest message){
        log.info("Request to send message");
        MessageResponse responseMessage = chatService.saveMessage(MessageRequestData.toMessageRequestData(message));
        simpMessageSendingOperations.convertAndSend(CHAT_SUB_PATH + message.getRoomId(), responseMessage);
    }
}
