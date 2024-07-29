package com.likelion.oegaein.global.config;

import com.likelion.oegaein.domain.chat.dto.UpdateDisconnectedAtRequest;
import com.likelion.oegaein.domain.chat.service.ChatRoomMemberService;
import com.likelion.oegaein.domain.chat.util.JwtUtil;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final String INTERCEPTOR_ERR_MSG = "소켓 인터셉터 에러가 발생했습니다";
    private final String NOT_FOUND_AUTH_HEADER_ERR_MSG = "헤더를 찾을 수 없습니다.";
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        boolean isNormalMessage = handleMessage(accessor);
        if(isNormalMessage){
            return message;
        }
        return null;
    }

    private boolean handleMessage(StompHeaderAccessor accessor) {
        try {
            if (StompCommand.CONNECT.equals(accessor.getCommand())) { // command == CONNECT
                Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
                String authorization = accessor.getFirstNativeHeader("Authorization");
                if(authorization == null) throw new IllegalArgumentException(NOT_FOUND_AUTH_HEADER_ERR_MSG);
                String accessToken = jwtUtil.getAccessToken(authorization);
                String email = jwtUtil.extractEmail(accessToken);
                Member member = memberRepository.findByEmail(email)
                                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
                Profile profile = member.getProfile();
                sessionAttributes.put("senderId", member.getId());
                sessionAttributes.put("name", profile.getName());
                sessionAttributes.put("photoUrl", member.getPhotoUrl());
                sessionAttributes.put("roomId", accessor.getFirstNativeHeader("roomId"));

                accessor.setSessionAttributes(sessionAttributes);
                return true;
            } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) { // command == DISCONNECT
                Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
                String name = (String) sessionAttributes.get("name");
                String roomId = (String) sessionAttributes.get("roomId");
                UpdateDisconnectedAtRequest dto = UpdateDisconnectedAtRequest.builder()
                        .name(name)
                        .roomId(roomId)
                        .build();
                // record disconnectedAt
                chatRoomMemberService.updateDisconnectedAt(dto);
                return true;
            } else {
                log.info("Message {}", accessor.getCommand());
                return true;
            }
        }catch (Exception e){
            log.error(INTERCEPTOR_ERR_MSG + ": " + e.getMessage());
            return false;
        }
    }
}

