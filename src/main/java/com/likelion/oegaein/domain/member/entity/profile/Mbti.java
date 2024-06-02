package com.likelion.oegaein.domain.member.entity.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
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

    @JsonCreator
    public static Mbti deserializer(String value) {
        for(Mbti mbti : Mbti.values()){
            if(mbti.getValue().equals(value)) {
                return mbti;
            }
        }
        return null;
    }

    @JsonValue
    public String serializer(){
        return value;
    }
}
