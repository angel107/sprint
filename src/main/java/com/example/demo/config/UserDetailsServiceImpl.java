package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.member.dto.CustomUser;
import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.service.MemberService;





@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	MemberService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("login id : " + username);

		// 실제 회원정보 가져오기
		MemberDTO dto = service.read(username);

		if (dto == null) {
			// 사용자 정보가 없다면 에러를 발생시킴
			throw new UsernameNotFoundException("");
		} else {
			// dto를 인증객체로 변환하여 반환
			return new CustomUser(dto);
		}

	}

}
