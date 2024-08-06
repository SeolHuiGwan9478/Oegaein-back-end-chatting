package com.likelion.oegaein.domain.chat.controller;

import com.likelion.oegaein.domain.chat.dto.*;
import com.likelion.oegaein.global.dto.ResponseDto;
import com.likelion.oegaein.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/api/v1/chatrooms") // 참가중인 채팅 목록 조회
    public ResponseEntity<ResponseDto> getChatRooms(Authentication authentication){
        log.info("Request to get chatrooms");
        FindChatRoomsResponse response = chatRoomService.findChatRooms(authentication);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/api/v1/chatrooms/unread")
    public ResponseEntity<ResponseDto> getUnreadMessages(Authentication authentication){
        log.info("Request to get unread messages");
        FindUnreadMessageResponse response = chatRoomService.findUnreadMessage(authentication);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/chatrooms/{roomid}") // 특정 채팅방 나가기
    public ResponseEntity<ResponseDto> deleteChatRoom(@PathVariable("roomid") String roomId, Authentication authentication){
        log.info("Request to delete chatroom-{}", roomId);
        DeleteChatRoomResponse response = chatRoomService.removeOneToOneChatRoom(roomId, authentication);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}