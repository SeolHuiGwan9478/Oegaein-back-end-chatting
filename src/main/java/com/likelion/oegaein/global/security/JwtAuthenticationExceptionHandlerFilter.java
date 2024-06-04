package com.likelion.oegaein.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.oegaein.domain.member.exception.MemberException;
import com.likelion.oegaein.global.dto.ErrorResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationExceptionHandlerFilter extends OncePerRequestFilter {
    private final String COMMON_ERR_MSG_KEY = "errorMessage";
    private final String AUTHORIZATION_ERR_MSG = "Invalid token error";
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch (Exception e){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Map<String, String> errors = new HashMap<>();
            errors.put(COMMON_ERR_MSG_KEY, AUTHORIZATION_ERR_MSG);
            ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                    .errorMessages(errors)
                    .build();
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);
            response.getOutputStream().print(jsonResponse);
        }
    }
}