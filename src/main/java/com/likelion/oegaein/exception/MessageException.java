package com.likelion.oegaein.exception;

public class MessageException extends RuntimeException{
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public MessageException(String message) {
        super(message);
    }
}
