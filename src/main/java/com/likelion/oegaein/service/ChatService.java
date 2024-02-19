package com.likelion.oegaein.service;

import com.likelion.oegaein.domain.Message;
import com.likelion.oegaein.dto.MessageRequestData;
import com.likelion.oegaein.dto.MessageResponse;
import com.likelion.oegaein.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;

    // save chatting content
    public MessageResponse saveMessage(MessageRequestData dto){
        Message message = Message.builder()
                .chattingRoomId(dto.getChattingRoomId())
                .senderName(dto.getSenderName())
                .message(dto.getMessage())
                .messageStatus(dto.getMessageStatus())
                .build();
        messageRepository.save(message);
        return new MessageResponse(message.getId());
    }
}
