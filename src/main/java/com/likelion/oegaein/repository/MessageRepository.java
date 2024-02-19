package com.likelion.oegaein.repository;

import com.likelion.oegaein.domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
