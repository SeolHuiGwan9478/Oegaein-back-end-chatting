package com.likelion.oegaein.domain.chat.exception;

public class ChatMemberException extends RuntimeException{
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ChatMemberException(String message) {
        super(message);
    }
}
