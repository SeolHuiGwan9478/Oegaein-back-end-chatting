package com.likelion.oegaein.domain.chat.repository;

import com.likelion.oegaein.domain.chat.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByRoomId(String roomId);

    List<Message> findByRoomIdAndDateAfterOrderByDateAsc(String roomId, LocalDateTime date);
    List<Message> findTop100ByRoomIdOrderByDateDesc(String roomId);


    @Query(value = "{'roomId': ?0}", sort = "{'date': -1}")
    List<Message> findTopByRoomId(String roomId, Pageable pageable);
}