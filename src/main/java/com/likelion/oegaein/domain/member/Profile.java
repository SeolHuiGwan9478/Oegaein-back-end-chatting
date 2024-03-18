package com.likelion.oegaein.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    @Column(name = "profile_id")
    private Long id;
    @Column(unique = true)
    private String name;
    private Gender gender;
    private int studentNo;
    private String major;
    private Date birthdate;
    private Dormitory dormitory;
    private Mbti mbti;
    private Smoking smoking;
    private List<SleepingHabit> sleepingHabit;
    private LifePattern lifePattern;
    private Outing outing;
    private CleaningCycle cleaningCycle;
    private Sensitivity soundSensitivity;
    private String introduction;
    private int star;
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
//    private Member member;
}
