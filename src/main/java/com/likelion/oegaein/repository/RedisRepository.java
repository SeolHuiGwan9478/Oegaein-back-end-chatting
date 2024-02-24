package com.likelion.oegaein.repository;

import com.likelion.oegaein.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private final RedisTemplate<String, LinkedList<Message>> redisTemplate;

    @Value("${spring.redis.expire}")
    private int expireTime;

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
        redisTemplate.expire(roomId, expireTime, TimeUnit.MINUTES);
    }

    // delete value
    public void delete(String roomId){
        redisTemplate.delete(roomId);
    }

    // delete all of data
    public void deleteAll(){
        redisTemplate.discard();
    }
}
