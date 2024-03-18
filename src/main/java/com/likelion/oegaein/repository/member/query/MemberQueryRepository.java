package com.likelion.oegaein.repository.member.query;

import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.exception.OneToOneChatException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
    private final EntityManager em;
    private final String NOT_FOUND_ONE_TO_ONE_CHAT_MEMBERS_ERR_MSG = "찾을 수 없는 사용자입니다.";

    public List<Member> findOneToOneChatMembers(List<Long> ids){
        String jpql = "select m from Member m" +
                " join fetch m.profile mp" +
                " where m.id in :ids";
        List<Member> findOneToOneChatMembers = em.createQuery(jpql, Member.class)
                .setParameter("ids", ids)
                .getResultList();
        if(findOneToOneChatMembers.size() != 2){
            throw new OneToOneChatException(NOT_FOUND_ONE_TO_ONE_CHAT_MEMBERS_ERR_MSG);
        }
        return findOneToOneChatMembers;
    }
}
