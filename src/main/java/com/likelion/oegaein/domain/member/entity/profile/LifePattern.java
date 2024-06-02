package com.likelion.oegaein.domain.member.entity.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LifePattern {
    MORNING("아침형"),
    NIGHT("새벽형");

    private final String value;

    @JsonCreator
    public static LifePattern deserializer(String value) {
        for(LifePattern lifePattern : LifePattern.values()){
            if(lifePattern.getValue().equals(value)) {
                return lifePattern;
            }
        }
        return null;
    }

    @JsonValue
    public String serializer(){
        return value;
    }
}