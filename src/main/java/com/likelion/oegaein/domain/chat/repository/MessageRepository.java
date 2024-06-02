package com.likelion.oegaein.domain.chat.repository;

import com.likelion.oegaein.domain.chat.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByRoomId(String roomId);
    List<Message> findByRoomIdAndDateAfterOrderByDateAsc(String roomId, LocalDateTime date);
    Page<Message> findByRoomIdOrderByDateAsc(String roomId, Pageable pageable);
}
