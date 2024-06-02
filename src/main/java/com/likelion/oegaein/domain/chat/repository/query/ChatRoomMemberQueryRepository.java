package com.likelion.oegaein.domain.chat.repository.query;

import com.likelion.oegaein.domain.chat.entity.ChatRoomMember;
import com.likelion.oegaein.domain.chat.exception.ChatMemberException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRoomMemberQueryRepository {
    private final EntityManager em;
    public Optional<ChatRoomMember> findByRoomIdAndName(String roomId, String name){
        String jpql = "select crm from ChatRoomMember crm" +
                " join fetch crm.chatRoom crmcr" +
                " join fetch crm.member crmm" +
                " join fetch crmm.profile crmmp" +
                " where crmcr.roomId = :roomId" +
                " and crmmp.name = :name";
        try {
            return Optional.of(em.createQuery(jpql, ChatRoomMember.class)
                    .setParameter("roomId", roomId)
                    .setParameter("name", name).getSingleResult());
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
