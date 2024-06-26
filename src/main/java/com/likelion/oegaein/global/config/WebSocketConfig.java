package com.likelion.oegaein.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final WebSocketInterceptor webSocketInterceptor;
    @Value("${WAS_SERVER_HOST}")
    private String HOST_IP;
    @Value("${RABBITMQ_DEFAULT_USER}")
    private String RABBITMQ_USER;
    @Value("${RABBITMQ_DEFAULT_PASS}")
    private String RABBITMQ_PASS;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/oegaein").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableStompBrokerRelay("/topic")
                .setSystemHeartbeatReceiveInterval(300000)
                .setSystemHeartbeatSendInterval(300000)
                .setRelayHost(HOST_IP)
                .setVirtualHost("/")
                .setRelayPort(61613)
                .setClientLogin(RABBITMQ_USER)
                .setClientPasscode(RABBITMQ_PASS)
                .setSystemLogin(RABBITMQ_USER)
                .setSystemPasscode(RABBITMQ_PASS);
    }

    public void configureClientInboundChannel(ChannelRegistration channelRegistration){
        channelRegistration.interceptors(webSocketInterceptor);
    }
}