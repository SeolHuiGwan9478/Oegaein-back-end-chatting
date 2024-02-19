package com.likelion.oegaein.domain;

import lombok.*;
<<<<<<< HEAD

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;
=======
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Builder
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String chattingRoomId; // 채팅방 ID
    private String senderName; // 보낸 회원 이름
    private String message; // 메시지 내용
    private MessageStatus messageStatus; // 메시지 타입
    @CreatedDate
    private LocalDateTime date; // 메시지 발신 날짜
>>>>>>> 5fd5858 (commit test)
}
