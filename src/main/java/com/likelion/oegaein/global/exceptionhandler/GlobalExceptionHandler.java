package com.likelion.oegaein.global.exceptionhandler;

import com.likelion.oegaein.global.dto.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // constants
    private final String COMMON_ERR_MSG_KEY = "errorMessage";
    private final String STATUS_CODE_500_ERR_MSG_VALUE = "내부 서버 오류";
    // EntityNotFoundException
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put(COMMON_ERR_MSG_KEY, ex.getMessage());
        final ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .errorMessages(errors)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex){
        Map<String, String> errors = new HashMap<>();
        errors.put(COMMON_ERR_MSG_KEY, STATUS_CODE_500_ERR_MSG_VALUE);
        ex.printStackTrace();
        final ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .errorMessages(errors)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
