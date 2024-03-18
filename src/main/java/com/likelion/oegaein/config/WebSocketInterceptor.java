package com.likelion.oegaein.config;

import com.likelion.oegaein.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Map;
import java.util.Objects;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {
    private final ChatRoomService chatRoomService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        handleMessage(accessor);
        return ChannelInterceptor.super.preSend(message, channel);
    }

    private void handleMessage(StompHeaderAccessor accessor) {
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String name = accessor.getFirstNativeHeader("name");
            String roomId = accessor.getFirstNativeHeader("roomId");
            Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
            sessionAttributes.put("name", name);
            sessionAttributes.put("roomId", roomId);
            accessor.setSessionAttributes(sessionAttributes);
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
            String name = (String) sessionAttributes.get("name");
            String roomId = (String) sessionAttributes.get("roomId");
            System.out.println(name);
            System.out.println(roomId);
        }
    }
}

