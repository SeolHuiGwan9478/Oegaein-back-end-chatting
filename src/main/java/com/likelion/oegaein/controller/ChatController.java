package com.likelion.oegaein.controller;

import com.likelion.oegaein.dto.*;
import com.likelion.oegaein.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
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

    @GetMapping("/api/v1/messages/{roomId}")
    public ResponseEntity<ResponseDto> getMessage(@PathVariable("roomId") String roomId){
        try {
            log.info("Request to get messages");
            FindMessagesResponse response = chatService.getMessages(roomId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
