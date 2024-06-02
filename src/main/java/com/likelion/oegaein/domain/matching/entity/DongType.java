package com.likelion.oegaein.domain.matching.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum DongType {
    A("A동"),
    B("B동"),
    C("C동"),
    D("D동"),
    E("E동");

    DongType(String value){
        this.value = value;
    }

    public final String value;

    @JsonCreator
    public DongType deserializerDong(String value){
        for(DongType dongType : DongType.values()){
            if(dongType.getValue().equals(value)){
                return dongType;
            }
        }
        return null;
    }
    @JsonValue
    public String serializerDong(){
        return value;
    }
}
