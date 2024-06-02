package com.likelion.oegaein.domain.member.entity.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    M("남성"),
    F("여성");

    private final String value;

    @JsonCreator
    public static Gender deserializer(String value) {
        for(Gender gender : Gender.values()){
            if(gender.getValue().equals(value)) {
                return gender;
            }
        }
        return null;
    }

    @JsonValue
    public String serializer(){
        return value;
    }
}