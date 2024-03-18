package com.likelion.oegaein.exception;

public class OneToOneChatException extends RuntimeException{
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public OneToOneChatException(String message) {
        super(message);
    }
}
