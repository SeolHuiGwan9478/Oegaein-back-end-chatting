package com.likelion.oegaein.domain.member.entity.member;

import com.likelion.oegaein.domain.member.entity.profile.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Transactional
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String googleName;
    @Setter
    private String photoUrl;
    private String refreshToken;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private Profile profile;
    private Boolean profileSetUpStatus;
}
