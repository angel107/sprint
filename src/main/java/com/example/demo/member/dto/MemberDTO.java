package com.example.demo.member.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
	String id;
	
	String password;
	
	String name;
	
	LocalDateTime regDate;
	
	LocalDateTime modDate;
	
	//사용자 권한추가( 사용자 : ROLE_USER, 관리자 : ROLE_ADMIN
	String role;
	
	boolean fromSocial; // 소셜 회원 여부 추가

	MultipartFile profileImage;

	String profileImagePath;
}
