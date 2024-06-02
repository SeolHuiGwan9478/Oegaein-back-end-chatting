package com.likelion.oegaein.domain.member.entity.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Sensitivity {
    SENSITIVE("예민한 편"),
    INSENSITIVE("둔감한 편");

    private final String value;

    @JsonCreator
    public static Sensitivity deserializer(String value) {
        for(Sensitivity sensitivity : Sensitivity.values()){
            if(sensitivity.getValue().equals(value)) {
                return sensitivity;
            }
        }
        return null;
    }

    @JsonValue
    public String serializer(){
        return value;
    }
}