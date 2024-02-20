package com.likelion.oegaein.service;

import com.likelion.oegaein.domain.Message;
import com.likelion.oegaein.domain.MessageStatus;
import com.likelion.oegaein.dto.MessageRequestData;
import com.likelion.oegaein.dto.MessageResponse;
import com.likelion.oegaein.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatService {
    // DI
    private final MessageRepository messageRepository;
    // constant
    private final String CHAT_JOIN_MSG = "님이 입장하였습니다.";

    // save chatting content
    public MessageResponse saveMessage(MessageRequestData dto){
        if(dto.getMessageStatus().equals(MessageStatus.JOIN)){ // JOIN 메시지 변환
            dto.setMessage(dto.getSenderName() + CHAT_JOIN_MSG);
        }
        Message message = Message.builder()
                .chattingRoomId(dto.getChattingRoomId())
                .senderName(dto.getSenderName())
                .message(dto.getMessage())
                .messageStatus(dto.getMessageStatus())
                .date(LocalDateTime.now())
                .build();
        messageRepository.save(message);
        return new MessageResponse(message);
    }
}
