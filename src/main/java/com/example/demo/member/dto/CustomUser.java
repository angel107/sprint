package com.example.demo.member.dto;

import java.util.Arrays;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;

// OAuth2User 인증 클래스를 상속 받아 소셜 로그인 시 사용할 사용자 정보 클래스 정의
@Getter
public class CustomUser extends User implements OAuth2User {

    // 소셜 로그인 시 비밀번호 확인을 위한 필드 추가
    String password;

    // OAuth2 사용자 속성을 저장할 맵
    Map<String, Object> attr;

    // MemberDTO를 받아 사용자 ID, 비밀번호, 권한 설정을 초기화하는 생성자
    public CustomUser(MemberDTO dto) {
        super(dto.getId(), dto.getPassword(), Arrays.asList(new SimpleGrantedAuthority(dto.getRole())));
        this.password = dto.getPassword();
    }

    // OAuth2User 인터페이스 구현: 사용자 속성 반환 메서드
    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    // OAuth2User 인터페이스 구현: 사용자 이름 반환 메서드 (여기서는 구현하지 않았음)
    @Override
    public String getName() {
        return null; // 구현 필요
    }

}
