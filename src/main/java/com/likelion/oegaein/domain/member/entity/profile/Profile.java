package com.likelion.oegaein.domain.member.entity.profile;

import com.likelion.oegaein.domain.member.entity.member.Member;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;
    @Column(unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int studentNo;
    @Enumerated(EnumType.STRING)
    private Major major;
    private Date birthdate;
    @Enumerated(EnumType.STRING)
    private Mbti mbti;
    @Enumerated(EnumType.STRING)
    private Smoking smoking;
    @Enumerated(EnumType.STRING)
    private LifePattern lifePattern;
    @Enumerated(EnumType.STRING)
    private Outing outing;
    @Enumerated(EnumType.STRING)
    private CleaningCycle cleaningCycle;
    @Enumerated(EnumType.STRING)
    private Sensitivity soundSensitivity;
    private String introduction;
    @Setter
    private double score;
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
