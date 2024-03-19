package com.likelion.oegaein.exception;

public class ChatMemberException extends RuntimeException{
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ChatMemberException(String message) {
        super(message);
    }
}
