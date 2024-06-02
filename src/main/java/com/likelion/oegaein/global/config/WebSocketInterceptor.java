package com.likelion.oegaein.global.config;

import com.likelion.oegaein.domain.chat.dto.UpdateDisconnectedAtRequest;
import com.likelion.oegaein.domain.chat.service.ChatRoomMemberService;
import com.likelion.oegaein.domain.chat.service.ChatRoomService;
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
    private final ChatRoomMemberService chatRoomMemberService;
    private final String INTERCEPTOR_ERR_MSG = "Socket Interceptor Error";

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
            // record disconnectedAt
            try {
                chatRoomMemberService.updateDisconnectedAt(dto);
            }catch (Exception e){
                log.error(INTERCEPTOR_ERR_MSG + ": " + e.getMessage());
            }
        }else{
            log.info("Message {}", accessor.getCommand());
        }
    }
}

