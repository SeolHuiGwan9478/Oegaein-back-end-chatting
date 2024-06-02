package com.likelion.oegaein.redis;

import com.likelion.oegaein.domain.chat.entity.Message;
import com.likelion.oegaein.domain.chat.entity.MessageStatus;
import com.likelion.oegaein.domain.chat.repository.MessageRepository;
import com.likelion.oegaein.domain.chat.repository.RedisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@SpringBootTest
public class RedisPerformanceTest {
    private StopWatch stopWatch;
    @BeforeEach
    public void setStopWatch(){
        stopWatch = new StopWatch("redisTest");
    }
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private RedisRepository redisRepository;

    @Test
    void 레디스_성능테스트(){
        Queue<Message> q = new LinkedList<>();
        for(int i = 0;i < 30;i++){
            Message message = Message.builder()
                    .roomId("room2")
                    .senderName("seol")
                    .message("test1")
                    .messageStatus(MessageStatus.MESSAGE)
                    .build();
            // db 저장
            messageRepository.save(message);
            // redis 저장
            q.add(message);
        }
        redisRepository.put("room2", q);

        stopWatch.start("DB");
        getMessages("room2");
        stopWatch.stop();

        stopWatch.start("Redis");
        getMessagesInCache("room2");
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
    }

    // MongoDB 직접 접근
    private List<Message> getMessages(String roomId){
        return messageRepository.findByRoomId(roomId);
    }

    // Redis 캐시 접근
    private List<Message> getMessagesInCache(String roomId){
        return redisRepository.get(roomId).stream().toList();
    }
}
