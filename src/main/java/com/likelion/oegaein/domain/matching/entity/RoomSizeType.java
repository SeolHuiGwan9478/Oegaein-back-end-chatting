package com.likelion.oegaein.domain.matching.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum RoomSizeType {
    Two("2인실", 2),
    Four("4인실", 4);

    RoomSizeType(String valueName, int valueNum){
        this.valueName = valueName;
        this.valueNum = valueNum;
    }
    public final String valueName;
    public final int valueNum;

    @JsonCreator
    public RoomSizeType deserializerRoomSize(String value){
        for(RoomSizeType roomSizeType : RoomSizeType.values()){
            if(roomSizeType.getValueName().equals(value)) {
                return roomSizeType;
            }
        }
        return null;
    }

    @JsonValue
    public String roomSizeSerializer(){
        return valueName;
    }
}

