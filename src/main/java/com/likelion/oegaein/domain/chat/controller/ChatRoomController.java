package com.likelion.oegaein.domain.chat.controller;

import com.likelion.oegaein.domain.chat.dto.*;
import com.likelion.oegaein.global.dto.ResponseDto;
import com.likelion.oegaein.domain.chat.service.ChatRoomService;
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
    public ResponseEntity<ResponseDto> getChatRooms(@RequestParam("memberid") Long memberId){
        log.info("Request to get chatrooms");
        FindChatRoomsResponse response = chatRoomService.findChatRooms(memberId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/api/v1/chatrooms") // 채팅방 생성
    public ResponseEntity<ResponseDto> postChatRoom(@RequestBody CreateChatRoomRequest dto){
        log.info("Request to post chatroom");
        CreateChatRoomResponse response = chatRoomService.createChatRoom(CreateChatRoomData.toCreateChatRoomData(dto));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/chatrooms/{roomid}") // 특정 채팅방 나가기
    public ResponseEntity<ResponseDto> deleteChatRoom(@PathVariable("roomid") String roomId, @RequestParam("memberid") Long memberId){
        log.info("Request to delete chatroom-{}", roomId);
        DeleteChatRoomResponse response = chatRoomService.removeOneToOneChatRoom(roomId, memberId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}