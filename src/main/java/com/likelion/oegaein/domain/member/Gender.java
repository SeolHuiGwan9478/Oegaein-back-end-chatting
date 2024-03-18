package com.likelion.oegaein.domain.member;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Gender {
    M("남성"),
    F("여성");

    private final String value;
}
