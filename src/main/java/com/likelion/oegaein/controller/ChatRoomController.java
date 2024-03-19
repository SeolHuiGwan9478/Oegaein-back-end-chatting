package com.likelion.oegaein.controller;

import com.likelion.oegaein.domain.chat.ChatRoomMember;
import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.dto.chat.CreateOneToOneChatRoomData;
import com.likelion.oegaein.dto.chat.CreateOneToOneChatRoomRequest;
import com.likelion.oegaein.dto.chat.CreateOneToOneChatRoomResponse;
import com.likelion.oegaein.dto.chat.DeleteOneToOneChatRoomResponse;
import com.likelion.oegaein.dto.global.ResponseDto;
import com.likelion.oegaein.repository.chat.ChatRoomMemberRepository;
import com.likelion.oegaein.repository.member.MemberRepository;
import com.likelion.oegaein.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberRepository memberRepository;
    @GetMapping("/api/v1/chatrooms") // 참가중인 채팅 목록 조회
    public ResponseEntity<ResponseDto> getChatRooms(){
        log.info("Request to get chatrooms");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/v1/onetoone-chatrooms") // 채팅방 생성
    public ResponseEntity<ResponseDto> postChatRoom(@RequestBody CreateOneToOneChatRoomRequest dto){
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

    @PostMapping("/user")
    @Transactional
    public ResponseEntity<ResponseDto> tempcreateuser(){
        log.info("user test");
        Member member = new Member();
        memberRepository.save(member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/chatroommembers")
    public ResponseEntity<ResponseDto> getchatrooms(){
        log.info("chatrooms test");
        List<ChatRoomMember> chatRoomMemberList = chatRoomMemberRepository.findAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
