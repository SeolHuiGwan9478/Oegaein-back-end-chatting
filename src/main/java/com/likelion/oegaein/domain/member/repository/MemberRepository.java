package com.likelion.oegaein.domain.member.repository;

import com.likelion.oegaein.domain.member.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
