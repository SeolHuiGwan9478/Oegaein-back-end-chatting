package com.likelion.oegaein.domain.member;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Smoking {
    SMOKER("흡연자"),
    NON_SMOKER("비흡연자");

    private final String value;
}
