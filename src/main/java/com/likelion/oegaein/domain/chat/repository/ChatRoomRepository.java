package com.likelion.oegaein.domain.chat.repository;

import com.likelion.oegaein.domain.chat.entity.ChatRoom;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @EntityGraph(attributePaths = {"matchingPost"})
    Optional<ChatRoom> findByRoomId(String roomId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from ChatRoom c where c.id = :id")
    Optional<ChatRoom> findByIdWithPessimisticLock(@Param("id") Long id);
}