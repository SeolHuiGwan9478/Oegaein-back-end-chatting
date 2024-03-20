package com.likelion.oegaein.config;

import com.likelion.oegaein.dto.chat.UpdateDisconnectedAtRequest;
import com.likelion.oegaein.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {
    private final ChatService chatRoomService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        handleMessage(accessor);
        return message;
    }

    private void handleMessage(StompHeaderAccessor accessor) {
        if (StompCommand.CONNECT.equals(accessor.getCommand())) { // command == CONNECT
            Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
            sessionAttributes.put("name", accessor.getFirstNativeHeader("name"));
            sessionAttributes.put("roomId", accessor.getFirstNativeHeader("roomId"));
            accessor.setSessionAttributes(sessionAttributes);
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) { // command == DISCONNECT
            Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
            String name = (String) sessionAttributes.get("name");
            String roomId = (String) sessionAttributes.get("roomId");
            UpdateDisconnectedAtRequest dto = UpdateDisconnectedAtRequest.builder()
                    .name(name)
                    .roomId(roomId)
                    .build();
            System.out.println(name);
            System.out.println(roomId);
            // record disconnectedAt
            chatRoomService.updateDisconnectedAt(dto);
        }
    }
}

