package com.example.demo.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.demo.member.dto.CustomUser;
import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.service.MemberService;

import java.util.Map;

/*
 * 구글 소셜 로그인 인증 커스텀 서비스
 * */

@Service // 스프링 컨테이너에 서비스 빈으로 등록합니다.
public class OAuthUserDetailsServiceImpl extends DefaultOAuth2UserService { // OAuth2 인증을 위한 서비스 클래스를 확장합니다.

    @Autowired
    MemberService memberService; // 멤버 서비스에 대한 의존성을 주입합니다.
    
    @Override // 부모 클래스의 메소드를 오버라이드하여 로그인 시 호출될 로직을 정의합니다.
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("--------------------------------");
        System.out.println("userRequest: " + userRequest); // 로그인 요청 정보를 출력합니다.

        String clientName = userRequest.getClientRegistration().getClientName(); // OAuth 클라이언트 이름을 가져옵니다. 예를 들어, "Google"
        System.out.println(clientName); // 클라이언트 이름을 출력합니다. 예: "Google"

        OAuth2User oAuth2User = super.loadUser(userRequest); // 부모 클래스의 메소드를 호출하여 OAuth2User 정보를 가져옵니다.
        oAuth2User.getAttributes().forEach((k,v) -> { // OAuth2User의 속성을 반복하여 출력합니다.
            System.out.println(k + ":" + v);
        });

        String email = null; // 이메일을 저장할 변수를 선언합니다.
        Map<String, Object> attributes = oAuth2User.getAttributes();
        if(clientName.equals("Google")){ // 클라이언트 이름이 "Google"인 경우
            email = oAuth2User.getAttribute("email"); //OAuth2User 속성에서 이메일을 가져옵니다.
        } else if (clientName.equals("kakao")) {
            email = ((Map<String, Object>) attributes.get("kakao_account")).get("email").toString();
        } else if (clientName.equals("naver")) {
            email = ((Map<String, Object>) oAuth2User.getAttribute("response")).get("email").toString();
        }

        System.out.println("EMAIL:" + email); // 이메일을 출력합니다.

        // 자동으로 회원가입을 처리합니다. 이때, 이메일 정보를 사용해 MemberDTO 객체를 생성합니다.
        MemberDTO memberDTO = memberService.saveSocialMember(email);
        memberDTO.setRole("ROLE_USER"); // 사용자 권한을 "ROLE_USER"로 설정합니다.

        // 최종적으로, 소셜 사용자 정보를 CustomUser 객체로 변환하여 반환합니다. 이를 통해 일반 로그인과 동일한 사용자 객체를 사용할 수 있습니다.

        return new CustomUser(memberDTO);
    }

}
