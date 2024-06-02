package com.likelion.oegaein.domain.matching.repository;

import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingPostRepository extends JpaRepository<MatchingPost, Long> {
}
