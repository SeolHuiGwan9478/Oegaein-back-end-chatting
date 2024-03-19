package com.likelion.oegaein.repository.chat;

import com.likelion.oegaein.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByChatRoomId(String roomId);
}
