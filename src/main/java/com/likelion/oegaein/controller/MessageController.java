package com.likelion.oegaein.controller;

import com.likelion.oegaein.dto.chat.FindMessagesResponse;
import com.likelion.oegaein.dto.chat.MessageRequest;
import com.likelion.oegaein.dto.chat.MessageRequestData;
import com.likelion.oegaein.dto.chat.MessageResponse;
import com.likelion.oegaein.dto.global.ResponseDto;
import com.likelion.oegaein.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {
    // DI
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final MessageService chatService;
    // constant
    private final String CHAT_SUB_PATH = "/topic/";

    @MessageMapping("/message")
    private void sendMessage(@Payload MessageRequest message, StompHeaderAccessor accessor){
        log.info("Request to send message");
        MessageResponse responseMessage = chatService.saveMessage(MessageRequestData.toMessageRequestData(message), accessor);
        simpMessageSendingOperations.convertAndSend(CHAT_SUB_PATH + responseMessage.getRoomId(), responseMessage);
    }

    @GetMapping("/api/v1/messages/{roomId}") // 메세지 내용 전체 조회
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
