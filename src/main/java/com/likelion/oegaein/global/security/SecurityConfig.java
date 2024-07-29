package com.likelion.oegaein.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.oegaein.domain.chat.util.JwtUtil;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(corsFilter()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // Authentication config
        http.authorizeHttpRequests((configure) -> {
            configure.requestMatchers("/api/v1/chatrooms**", "/api/v1/chatrooms/**").authenticated();
            configure.requestMatchers("/api/v1/chatroommembers**").authenticated();
            configure.requestMatchers("/api/v1/messages**").authenticated();
            configure.requestMatchers("/oegaein").permitAll();
            configure.anyRequest().permitAll();
        });
        http.addFilterBefore(jwtAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationExceptionHandlerFilter(), JwtAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // cors 설정
        config.setAllowedOriginPatterns(List.of("http://127.0.0.1:3000", "http://localhost:3000"));
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.addExposedHeader("Set-Cookie");
        config.setAllowCredentials(true);
        // source -> config 적용
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(jwtUtil, memberRepository);
    }

    @Bean
    public JwtAuthenticationExceptionHandlerFilter jwtAuthenticationExceptionHandlerFilter(){
        return new JwtAuthenticationExceptionHandlerFilter(objectMapper);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception{
        return (web) -> web.ignoring()
                .requestMatchers("/error");
    }
}