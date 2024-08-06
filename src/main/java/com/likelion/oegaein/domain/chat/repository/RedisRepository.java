package com.likelion.oegaein.domain.chat.repository;

import com.likelion.oegaein.domain.chat.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
@RequiredArgsConstructor
public class RedisRepository {
    private final RedisTemplate<String, LinkedList<Message>> redisTemplate;

    // checking contains key
    public Boolean containsKey(String roomId){
        return redisTemplate.hasKey(roomId);
    }

    // get value
    public LinkedList<Message> get(String roomId){
        return redisTemplate.opsForValue().get(roomId);
    }

    // put value
    public void put(String roomId, Queue<Message> messageQueue){
        redisTemplate.opsForValue().set(roomId, new LinkedList<>(messageQueue));
    }

    // delete values
    public void delete(String roomId){
        redisTemplate.delete(roomId);
    }

    public Map<String, LinkedList<Message>> getAllData() {
        Set<String> keys = redisTemplate.keys("*");
        Map<String, LinkedList<Message>> allData = new HashMap<>();
        for (String key : keys) {
            try{
                LinkedList<Message> value = get(key);
                if (value != null) {
                    allData.put(key, value);
                }
            }catch (Exception e){
                if(e.getClass() == SerializationException.class){
                    delete(key);
                }else{
                    log.error(e.getMessage());
                }
            }
        }
        return allData;
    }
    // delete all of data
    public void deleteAll(){
        redisTemplate.discard();
    }
}
