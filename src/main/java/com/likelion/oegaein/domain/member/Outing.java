package com.likelion.oegaein.domain.member;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Outing {
    HOMEBODY("집돌이", "집순이"),
    ITCHY_FEET("밖돌이", "밖순이");

    private final String maleValue;
    private final String femaleValue;
}
