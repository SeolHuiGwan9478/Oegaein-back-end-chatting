package com.likelion.oegaein.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
<<<<<<< HEAD
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
=======
        registry.addEndpoint("/oegaein").setAllowedOriginPatterns("*").withSockJS();
>>>>>>> 5fd5858 (commit test)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
<<<<<<< HEAD
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/chatroom", "/user");
        registry.setUserDestinationPrefix("/user");
=======
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("chatroom");
>>>>>>> 5fd5858 (commit test)
    }
}
