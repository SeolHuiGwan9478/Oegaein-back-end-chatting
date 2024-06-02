package com.likelion.oegaein.domain.member.entity.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CleaningCycle {
    EVERYDAY("매일"),
    WEEK("주 1회 이상"),
    MONTH("월 1회 이상"),
    SOMETIMES("생각날 때 가끔");

    private final String value;

    @JsonCreator
    public static CleaningCycle deserializer(String value) {
        for(CleaningCycle cleaningCycle : CleaningCycle.values()){
            if(cleaningCycle.getValue().equals(value)) {
                return cleaningCycle;
            }
        }
        return null;
    }

    @JsonValue
    public String serializer(){
        return value;
    }
}
