package com.likelion.oegaein.domain.member;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CleaningCycle {
    EVERYDAY("매일"),
    WEEK("주 1회 이상"),
    MONTH("월 1회 이상"),
    SOMETIMES("생각날 때 가끔");

    private final String value;
}
