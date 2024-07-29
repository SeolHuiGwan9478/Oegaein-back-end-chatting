package com.likelion.oegaein.chat;

import com.likelion.oegaein.domain.chat.entity.ChatRoom;
import com.likelion.oegaein.domain.chat.repository.ChatRoomRepository;
import com.likelion.oegaein.domain.chat.service.ChatRoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class ChatRoomTest {
//    @Autowired
//    ChatRoomRepository chatRoomRepository;
//
//    @Autowired
//    ChatRoomService chatRoomService;
//
//    @Test
//    @DisplayName("비관적 동시성 제어 테스트")
//    @Rollback(value = true)
//    public void 비관적_락킹_테스트() throws InterruptedException {
//        int count = 20;
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        CountDownLatch latch = new CountDownLatch(count);
//        for(int i = 0;i < count;i++){
//            executorService.execute(() -> {
//                try{
//                    chatRoomService.decreaseMemberCount(1L);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                latch.countDown();
//            });
//        }
//        latch.await();
//
//        ChatRoom chatRoom = chatRoomRepository.findById(1L).get();
//        System.out.println(chatRoom.getMemberCount());
//        org.assertj.core.api.Assertions.assertThat(chatRoom.getMemberCount()).isNotEqualTo(2);
//    }
}
