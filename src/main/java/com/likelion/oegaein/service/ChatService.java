package com.likelion.oegaein.service;

import com.likelion.oegaein.domain.Message;
import com.likelion.oegaein.domain.MessageStatus;
import com.likelion.oegaein.dto.MessageRequestData;
import com.likelion.oegaein.dto.MessageResponse;
import com.likelion.oegaein.repository.MessageRepository;
import com.likelion.oegaein.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class ChatService {
    // DI
    private final MessageRepository messageRepository;
    private final RedisRepository redisRepository;
    // constant
    private final String CHAT_JOIN_MSG = "님이 입장하였습니다.";
    private final int MAX_CACHE_SIZE_EACH_ROOM = 3;

    // save chatting content
    public MessageResponse saveMessage(MessageRequestData dto){
        if(dto.getMessageStatus().equals(MessageStatus.JOIN)){ // JOIN 메시지 변환
            dto.setMessage(dto.getSenderName() + CHAT_JOIN_MSG);
        }
        Message message = Message.builder()
                .roomId(dto.getRoomId())
                .senderName(dto.getSenderName())
                .message(dto.getMessage())
                .messageStatus(dto.getMessageStatus())
                .date(LocalDateTime.now())
                .build(); // setting message
        // check in redis cache
        if(!redisRepository.containsKey(dto.getRoomId())){
            Queue<Message> q = new LinkedList<>();
            q.add(message);
            redisRepository.put(dto.getRoomId(), q);
        }else{
            Queue<Message> q = redisRepository.get(dto.getRoomId());
            q.add(message);
            if(q.size() >= MAX_CACHE_SIZE_EACH_ROOM){
                Queue<Message> tmpQ = new LinkedList<>();
                for(int i = 0;i < MAX_CACHE_SIZE_EACH_ROOM;i++){
                    tmpQ.add(q.poll());
                }
                commitMessageCache(tmpQ);
            }
            redisRepository.put(dto.getRoomId(), q);
        }
        return new MessageResponse(message);
    }

    // write back pattern
    private void commitMessageCache(Queue<Message> messageQueue){
        for(int i = 0;i < MAX_CACHE_SIZE_EACH_ROOM;i++){
            Message message = messageQueue.poll();
            messageRepository.save(message);
        }
    }
}
