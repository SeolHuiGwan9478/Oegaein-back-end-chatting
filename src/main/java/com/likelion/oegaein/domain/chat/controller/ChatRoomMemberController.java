package com.likelion.oegaein.domain.chat.controller;

import com.likelion.oegaein.domain.chat.dto.CreateChatRoomMemberData;
import com.likelion.oegaein.domain.chat.dto.CreateChatRoomMemberRequest;
import com.likelion.oegaein.domain.chat.dto.CreateChatRoomMemberResponse;
import com.likelion.oegaein.domain.chat.dto.UpdateDisconnectedAtRequest;
import com.likelion.oegaein.domain.chat.entity.ChatRoomMember;
import com.likelion.oegaein.domain.chat.repository.query.ChatRoomMemberQueryRepository;
import com.likelion.oegaein.domain.chat.service.ChatRoomMemberService;
import com.likelion.oegaein.global.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatRoomMemberController {
    private final ChatRoomMemberService chatRoomMemberService;

    @PostMapping("/api/v1/chatroommembers")
    public ResponseEntity<ResponseDto> postChatRoomMember(@RequestBody CreateChatRoomMemberRequest dto){
        log.info("Request to post chatroom member");
        CreateChatRoomMemberResponse response = chatRoomMemberService.createChatRoomMember(
                CreateChatRoomMemberData.toCreateChatRoomMemberData(dto));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
