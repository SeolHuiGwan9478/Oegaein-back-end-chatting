package com.likelion.oegaein.controller;

import com.likelion.oegaein.dto.ChatRoom;
import com.likelion.oegaein.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    @PostMapping("/v1/chat")
    public ChatRoom createRoom(@RequestBody String name){
        return chatService.createRoom(name);
    }

    @GetMapping("/v1/chat")
    public List<ChatRoom> findAllRoom(){
        return chatService.findAllRoom();
    }
}
