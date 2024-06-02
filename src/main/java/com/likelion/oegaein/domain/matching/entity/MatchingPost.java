package com.likelion.oegaein.domain.matching.entity;

import com.likelion.oegaein.domain.member.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MatchingPost {
    @Id
    @GeneratedValue
    @Column(name = "matching_post_id")
    private Long id; // PK

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private DongType dongType; // 기숙사 동(* A,B,C,D,E)
    @Enumerated(EnumType.STRING)
    private RoomSizeType roomSizeType; // 기숙사 방 사이즈(* 2인실/4인실)

    private int targetNumberOfPeople; // 목표 인원 수

    private LocalDate deadline;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus; // 매칭 상태, WAITING, COMPLETED

    @ManyToOne(fetch = FetchType.LAZY) // 변경 필요
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author_id")
    private Member author;
}
