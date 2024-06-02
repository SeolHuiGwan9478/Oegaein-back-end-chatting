package com.likelion.oegaein.domain.member.entity.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Smoking {
    SMOKER("흡연자"),
    NON_SMOKER("비흡연자");

    private final String value;

    @JsonCreator
    public static Smoking deserializer(String value) {
        for(Smoking smoking : Smoking.values()){
            if(smoking.getValue().equals(value)) {
                return smoking;
            }
        }
        return null;
    }

    @JsonValue
    public String serializer(){
        return value;
    }
}
