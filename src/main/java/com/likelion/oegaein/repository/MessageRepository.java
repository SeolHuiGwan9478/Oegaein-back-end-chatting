package com.likelion.oegaein.repository;

import com.likelion.oegaein.domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByRoomId(String roomId);
}
