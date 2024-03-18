package com.likelion.oegaein.domain.member;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Mbti {
    ESFJ("ESFJ"),
    ESFP("ESFP"),
    ESTJ("ESTJ"),
    ESTP("ESTP"),
    ENFJ("ENFJ"),
    ENFP("ENFP"),
    ENTJ("ENTJ"),
    ENTP("ENTP"),
    ISFJ("ISFJ"),
    ISFP("ISFP"),
    ISTJ("ISTJ"),
    ISTP("ISTP"),
    INFJ("INFJ"),
    INFP("INFP"),
    INTJ("INTJ"),
    INTP("INTP");

    private final String value;
}
