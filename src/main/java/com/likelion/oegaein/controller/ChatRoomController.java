package com.likelion.oegaein.controller;

import com.likelion.oegaein.dto.chat.CreateOneToOneChatRoomData;
import com.likelion.oegaein.dto.chat.CreateOneToOneChatRoomRequest;
import com.likelion.oegaein.dto.chat.CreateOneToOneChatRoomResponse;
import com.likelion.oegaein.dto.chat.DeleteOneToOneChatRoomResponse;
import com.likelion.oegaein.dto.global.ResponseDto;
import com.likelion.oegaein.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    @GetMapping("/api/v1/chatrooms") // 참가중인 채팅 목록 조회
    public ResponseEntity<ResponseDto> getChatRooms(){
        log.info("Request to get chatrooms");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/v1/onetoone-chatrooms") // 채팅방 생성
    public ResponseEntity<ResponseDto> postChatRoom(CreateOneToOneChatRoomRequest dto){
        log.info("Request to post chatroom");
        CreateOneToOneChatRoomResponse response = chatRoomService.createOneToOneChatRoom(CreateOneToOneChatRoomData.toCreateChatRoomData(dto));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/api/v1/onetoone-chatrooms/{roomid}") // 특정 채팅방 나가기
    public ResponseEntity<ResponseDto> deleteChatRoom(@PathVariable("roomid") String roomId){
        log.info("Request to delete chatroom-{}", roomId);
        DeleteOneToOneChatRoomResponse response = chatRoomService.removeOneToOneChatRoom(roomId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
