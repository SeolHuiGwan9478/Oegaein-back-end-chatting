package com.likelion.oegaein.domain.member;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SleepingHabit {
    SNORER("코골이"),
    BRUXISM("이갈이"),
    TALKER("잠꼬대"),
    SILENT("무소음");

    private final String value;
}
