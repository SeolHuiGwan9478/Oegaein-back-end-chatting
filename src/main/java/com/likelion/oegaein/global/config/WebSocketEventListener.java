package com.likelion.oegaein.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event){
        log.info("Oegaein chat server connection completed: {}",event.getTimestamp());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        log.info("Oegaein chat server disconnection completed: {}",event.getTimestamp());
    }
}
