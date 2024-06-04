package com.likelion.oegaein.domain.member.exception;

public class MemberException extends RuntimeException{
    public MemberException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}