package com.likelion.oegaein.global.util;

import com.likelion.oegaein.domain.chat.entity.Message;
import com.likelion.oegaein.domain.chat.repository.CustomMessageRepository;
import com.likelion.oegaein.domain.chat.repository.MessageRepository;
import com.likelion.oegaein.domain.chat.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisBackupScheduler {
    private final MessageRepository messageRepository;
    private final CustomMessageRepository customMessageRepository;
    private final RedisRepository redisRepository;
    private final int MAX_CACHE_SIZE_EACH_ROOM = 100;
    @Scheduled(cron = "0 0 * * * *")
    public void backupMessagesInRedis(){
        log.info("Backup messages in redis");
        Map<String, LinkedList<Message>> allMessagesInRedis = redisRepository.getAllData();
        for(String key : allMessagesInRedis.keySet()){
            LinkedList<Message> value = allMessagesInRedis.get(key);
            if(value.isEmpty()){ // remove empty queue
                redisRepository.delete(key);
                continue;
            }
            // get messages in redis
            List<Message> valueOfList = new ArrayList<>(value);
            for(int idx = 0;idx < valueOfList.size();idx++){
                Message message = valueOfList.get(idx);
                if(message.getId() == null && idx != valueOfList.size()-1){
                    List<Message> savePointList = valueOfList.subList(idx+1, valueOfList.size());
                    log.info("bulk insert {} counts", valueOfList.size()-idx);
                    customMessageRepository.bulkInsert(savePointList);
                    break;
                }
            }
            if(valueOfList.size() > MAX_CACHE_SIZE_EACH_ROOM){
                List<Message> newValueOfList = new ArrayList<>(valueOfList
                        .subList(valueOfList.size()-MAX_CACHE_SIZE_EACH_ROOM, valueOfList.size()));
                Queue<Message> q = new LinkedList<>(newValueOfList);
                redisRepository.put(key, q);
            }
        }
        log.info("Completed successfully backup operation");
    }
}
