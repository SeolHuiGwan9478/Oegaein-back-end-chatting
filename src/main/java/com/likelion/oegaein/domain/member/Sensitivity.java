package com.likelion.oegaein.domain.member;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Sensitivity {
    SENSITIVE("예민한 편"),
    INSENSITIVE("둔감한 편");

    private final String value;
}
