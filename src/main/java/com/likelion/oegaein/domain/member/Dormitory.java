package com.likelion.oegaein.domain.member;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Dormitory {
    A("A동"),
    B("B동"),
    C("C동"),
    D("D동"),
    E("E동"),
    F("F동"),
    ETC("기타");

    private final String value;
}
