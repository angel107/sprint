package com.example.demo.config;


import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.demo.member.dto.CustomUser;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
소셜 로그인 후 처리
*/
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

   // 비밀번호 인코더를 BCrypt 알고리즘으로 설정
   PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

   @Override
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
       
       // 인증에 성공한 사용자의 정보를 가져옴
       CustomUser customUser = (CustomUser) authentication.getPrincipal();

       // 인증에 성공한 사용자의 이름(아이디)를 가져옴
       String username = authentication.getName();

       // 사용자의 비밀번호를 가져옴
       String pw = customUser.getPassword();
       log.info("Authentication username: {}, password: {}", username, pw);
       // 사용자의 비밀번호가 '1111'이 맞는지 확인
       boolean matchResult = passwordEncoder.matches("1111", pw);
       
       // 비밀번호가 '1111'인 경우, 회원정보 수정 페이지로 리다이렉트
       if(matchResult){
           response.sendRedirect("/member/modify?id="+username);
       } else {
           // 비밀번호가 '1111'이 아닌 경우, 메인 페이지로 리다이렉트
           response.sendRedirect("/");
       }

   }
}