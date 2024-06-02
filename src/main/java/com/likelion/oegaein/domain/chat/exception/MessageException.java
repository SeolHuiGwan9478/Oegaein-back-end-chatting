package com.likelion.oegaein.domain.chat.exception;

public class MessageException extends RuntimeException{
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public MessageException(String message) {
        super(message);
    }
}
