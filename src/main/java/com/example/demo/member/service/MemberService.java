package com.example.demo.member.service;

import org.springframework.data.domain.Page;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.entity.Member;

// 회원 관련 서비스를 위한 인터페이스 정의
public interface MemberService {

    Page<MemberDTO> getList(int pageNumber); // 페이지 번호에 해당하는 회원 목록 조회

    boolean register(MemberDTO dto); // 회원 등록, 성공적으로 등록되면 true 반환

    MemberDTO read(String id); // 회원 단건 조회, 회원의 아이디를 이용하여 조회

    void modify(MemberDTO dto); // 회원 정보 수정, 수정할 회원의 정보를 담은 DTO를 매개변수로 받음

    MemberDTO saveSocialMember(String email); // 소셜 로그인한 이메일로 회원가입, 이메일 주소를 이용하여 소셜 회원 정보 저장

    MemberDTO findMemberById(String id); // 아이디를 이용해 회원 정보를 조회하고, 해당 정보를 MemberDTO로 반환

    String findNameById(String id); // 회원의 아이디를 이용하여 그 회원의 이름 조회

    // 엔티티를 DTO로 변환하는 메소드, Member 엔티티 객체를 MemberDTO 객체로 변환하여 반환
    default MemberDTO entityToDto(Member entity) {
        MemberDTO dto = MemberDTO.builder()
                .id(entity.getId())
                .password(entity.getPassword())
                .name(entity.getName())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .role(entity.getRole()) // 등급 정보 추가
                .fromSocial(entity.isFromSocial()) // 소셜 로그인 여부
                .build();

        return dto;
    }

    // DTO를 엔티티로 변환하는 메소드, MemberDTO 객체를 Member 엔티티 객체로 변환하여 반환
    default Member dtoToEntity(MemberDTO dto) {
        Member entity = Member.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .name(dto.getName())
                .role(dto.getRole()) // 등급 정보
                .fromSocial(dto.isFromSocial()) // 소셜 로그인 여부
                .build();
        return entity;
    }

}
