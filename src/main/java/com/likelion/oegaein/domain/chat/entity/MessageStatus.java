package com.likelion.oegaein.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum MessageStatus {
    MESSAGE("MESSAGE"),
    LEAVE("LEAVE");

    private String value;
    MessageStatus(String value){
        this.value = value;
    }

    @JsonCreator
    public static MessageStatus deserializeMessageStatus(String value){
        for(MessageStatus messageStatus : MessageStatus.values()){
            if(messageStatus.getValue().equals(value)){
                return messageStatus;
            }
        }
        return null;
    }

    @JsonValue
    public String serializeMessageStatus(){
        return this.value;
    }
}
