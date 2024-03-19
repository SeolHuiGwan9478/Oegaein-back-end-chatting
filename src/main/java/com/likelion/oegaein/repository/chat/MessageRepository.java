package com.likelion.oegaein.repository.chat;

import com.likelion.oegaein.domain.chat.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByRoomId(String roomId);
    List<Message> findByRoomIdAndDateAfter(String roomId, LocalDateTime date);
    Page<Message> findByRoomIdOrderByDateDesc(String roomId, Pageable pageable);
}
