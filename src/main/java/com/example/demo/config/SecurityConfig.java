package com.example.demo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 스프링 시큐리티 설정 클래스
 * */
@Configuration //스프링의 설정 클래스임을 명시
@EnableWebSecurity //스프링 시큐리티 설정 클래스임을 명시
public class SecurityConfig {
	@Autowired
	private AuthenticationFailureHandler customAuthenticationFailureHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 사용자 인증을 처리하는 메소드
	// 메소드에 @Bean을 붙이면, 반환값인 인증 필터 객체가 스프링 컨테이너에 등록됨
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// 보안규칙 (메뉴별 접근제한 설정)
	    http.authorizeHttpRequests()
				.requestMatchers("/register").permitAll() //회원가입은 아무나 접근 가능
				.requestMatchers("/email/**").permitAll()
				.requestMatchers("/chat/*").permitAll() //회원가입은 아무나 접근 가능
				.requestMatchers("/assets/*", "/css/*", "/js/*", "/vendor/**", "/img/**","/files/*","/customlogin*").permitAll() //리소스는 아무나 접근 가능
				.requestMatchers("/").permitAll() //메인화면은 로그인한 사용자이면 접근 가능
				.requestMatchers("/runningBoard/**").hasAnyRole("ADMIN","USER") //게시물 관리는 관리자 또는 사용자이면 접근 가능
				.requestMatchers("/marathonBoard/**").hasAnyRole("ADMIN","USER") //게시물 관리는 관리자 또는 사용자이면 접근 가능
				.requestMatchers("/diaryBoard/**").hasAnyRole("ADMIN","USER")
				.requestMatchers("/qnaBoard/**").hasAnyRole("ADMIN","USER")
				.requestMatchers("/uploadfile/**").permitAll() //파일첨부 이미지 모두 접근 가능
				.requestMatchers("/rBoardcomment/*","/mBoardcomment/*","/dBoardComment/*","/qBoardComment/*").hasAnyRole("ADMIN","USER")
				.requestMatchers("/joinMember/*").hasAnyRole("ADMIN","USER")
				.requestMatchers("/search/*").permitAll()
				.requestMatchers("/member/*").hasAnyRole("ADMIN","USER"); // 소셜로그인 회원 정보를 수정하기 위해 변경

	    http.formLogin();
        http.csrf().disable(); //csrf는 get을 제외하여 상태값을 위조(변경)할 수있는 post,put,delete 메소드를 막음
        http.logout()
	        .logoutUrl("/logout")
	        .logoutSuccessUrl("/");
        ; // 로그아웃 처리


     // aouth 로그인이 가능하도록 설정
     		http.oauth2Login()
     				.successHandler(new CustomAuthenticationSuccessHandler()); // 핸들러 등록

        //커스텀 로그인 페이지 적용
		http.formLogin()
		.loginPage("/customlogin")
		.loginProcessingUrl("/login")
		.successHandler(
                new AuthenticationSuccessHandler() {
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
						response.sendRedirect("/");
					}
                }
        )
		.failureHandler(customAuthenticationFailureHandler)

	    .permitAll(); // 접근 권한
		return http.build();
	}

}
