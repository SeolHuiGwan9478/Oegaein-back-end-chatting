package com.likelion.oegaein.domain.chat.util;

import com.likelion.oegaein.domain.member.entity.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@PropertySource("classpath:application.yaml")
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final String TOKEN_SUBJECT = "oegaein";
    private final int ACCESS_TOKEN_EXPIRE = 1000 * 60 * 60 * 2;
    private final int REFRESH_TOKEN_EXPIRE = 1000 * 60 * 60 * 24;

    public String generateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>(); // claim 생성
        claims.put("email", member.getEmail());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(TOKEN_SUBJECT)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken(Member member) {
        Map<String, Object> claims = new HashMap<>(); // claim 생성
        claims.put("email", member.getEmail());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(TOKEN_SUBJECT)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return (String) claims.getOrDefault("email", "");
    }

    public Date extractExpiration(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody()
                .getExpiration();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token){
        return (!isTokenExpired(token) && !extractEmail(token).isBlank());
    }

    // access_token 가져오기
    public String getAccessToken(String header) {
        return header.split(" ")[1].trim(); // Bearer 제외
    }

    // refresh_token 가져오기
    private String getRefreshToken(Cookie cookie){
        return cookie.getValue();
    }
}