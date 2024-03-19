package com.likelion.oegaein.domain.member;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class Profile {
    @Id @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int studentNo;
    private String major;
    private Date birthdate;
    @Enumerated(EnumType.STRING)
    private Dormitory dormitory;
    @Enumerated(EnumType.STRING)
    private Mbti mbti;
    @Enumerated(EnumType.STRING)
    private Smoking smoking;
    //private List<SleepingHabit> sleepingHabit;
    @Enumerated(EnumType.STRING)
    private LifePattern lifePattern;
    @Enumerated(EnumType.STRING)
    private Outing outing;
    @Enumerated(EnumType.STRING)
    private CleaningCycle cleaningCycle;
    @Enumerated(EnumType.STRING)
    private Sensitivity soundSensitivity;
    private String introduction;
    private int star;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "profile")
    private Member member;
}
