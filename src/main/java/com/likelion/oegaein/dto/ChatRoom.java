package com.likelion.oegaein.dto;

import com.likelion.oegaein.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class ChatRoom {
    private String roomId;
    private String name;
    @Builder.Default
    private Set<WebSocketSession> sessions = new HashSet<>();

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService){
        if(chatMessage.getMessageType().equals(MessageType.ENTER)){
            sessions.add(session);;
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessage, chatService);
    }

    private void sendMessage(ChatMessage message, ChatService chatService){
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
}
