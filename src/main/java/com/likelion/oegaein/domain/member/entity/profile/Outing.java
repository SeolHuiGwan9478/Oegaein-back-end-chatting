package com.likelion.oegaein.domain.member.entity.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Outing {
    HOMEBODY("집순이"),
    ITCHY_FEET("밖순이");

    private final String value;

    @JsonCreator
    public static Outing deserializer(String value) {
        for(Outing outing : Outing.values()){
            if(outing.getValue().equals(value)) {
                return outing;
            }
        }
        return null;
    }

    @JsonValue
    public String serializer(){
        return value;
    }
}
