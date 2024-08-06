package com.likelion.oegaein.domain.chat.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messages")
public class Message implements Comparable<Message>{
    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private String id;
    private String roomId; // 채팅방 ID
    private Long senderId;
    private String senderName; // 보낸 회원 이름
    private String photoUrl;
    private String message; // 메시지 내용
    private MessageStatus messageStatus; // 메시지 타입

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @CreatedDate
    private LocalDateTime date; // 메시지 발신 날짜

    @Override
    public int compareTo(Message o) {
        return this.date.compareTo(o.date);
    }
}
