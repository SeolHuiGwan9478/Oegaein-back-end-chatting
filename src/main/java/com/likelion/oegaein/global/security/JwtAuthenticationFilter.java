package com.likelion.oegaein.global.security;

import com.likelion.oegaein.domain.chat.util.JwtUtil;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.exception.MemberException;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(isRequiredFiltering(authHeader)){
            String accessToken = jwtUtil.getAccessToken(authHeader);
            Boolean isValidAccessToken = jwtUtil.validateToken(accessToken);
            if(isValidAccessToken.equals(Boolean.FALSE)){ // 유효하지 않은 액세스 토큰
                throw new MemberException("Auth Error: Invalid Access Token");
            }
            String email = jwtUtil.extractEmail(accessToken);
            Member authMember = memberRepository.findByEmail(email) // 사용자 찾기
                    .orElseThrow(() -> new MemberException("Not Found: Member"));
            GoogleOauthMemberDetails googleOauthMemberDetails = new GoogleOauthMemberDetails(authMember);
            Authentication authentication = new UsernamePasswordAuthenticationToken(googleOauthMemberDetails, null, googleOauthMemberDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // JWT 토큰 검증이 필요한 경우에만 동작
        filterChain.doFilter(request, response);
    }

    private Boolean isRequiredFiltering(String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}