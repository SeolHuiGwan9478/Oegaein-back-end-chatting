package com.likelion.oegaein.domain.chat.repository;


import com.likelion.oegaein.domain.chat.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.BulkOperationException;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CustomMessageRepository {
    private final MongoTemplate mongoTemplate;
    public void bulkInsert(List<Message> messages){
        if(messages.isEmpty()){
            return;
        }
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Message.class);
        bulkOps.insert(messages);
        try{
            bulkOps.execute();
        }catch (BulkOperationException e){
            log.error("Message In Redis Bulk Update Error");
        }
    }
}
