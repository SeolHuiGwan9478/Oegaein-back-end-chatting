package com.likelion.oegaein.repository.member;

import com.likelion.oegaein.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
