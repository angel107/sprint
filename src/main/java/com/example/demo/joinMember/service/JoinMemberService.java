package com.example.demo.joinMember.service;

import java.util.List;

import com.example.demo.joinMember.dto.JoinMemberDTO; // 참여 회원 DTO를 위한 import 문
import com.example.demo.joinMember.entity.JoinMember; // 참여 회원 엔티티를 위한 import 문
import com.example.demo.member.entity.Member; // 회원 엔티티를 위한 import 문
import com.example.demo.runningBoard.entity.Running; // 러닝 엔티티를 위한 import 문

// 참여 회원 관련 서비스를 정의하는 인터페이스
public interface JoinMemberService {

    // 러닝 참석 기능을 정의하는 메서드
    JoinMemberDTO joinRunning(int runningNo, String runnerId);

    // 특정 러닝 이벤트의 참석자 명단을 조회하는 메서드
    List<JoinMemberDTO> getList(int runningNo);

    // 특정 사용자가 이미 특정 러닝 이벤트에 참여했는지를 확인하는 메서드
    boolean isAlreadyJoined(int runningNo, String runnerId);

    // 참석 취소 기능을 정의하는 메서드
    int cancelJoin(int runningNo, String runnerId);

    // DTO를 엔티티로 변환하는 기본 메서드
    default JoinMember dtoToEntity(JoinMemberDTO dto) {

        Running running = Running.builder().no(dto.getRunningNo()).build(); // Running 객체 생성

        Member member = Member.builder().id(dto.getRunnerId()).build(); // Member 객체 생성

        JoinMember entity = JoinMember.builder().runningNo(running).joinNo(dto.getJoinNo()).runnerId(member).build(); // JoinMember 엔티티 객체 생성

        return entity; // 생성된 엔티티 객체 반환
    }

    // 엔티티를 DTO로 변환하는 기본 메서드
    default JoinMemberDTO entityToDto(JoinMember entity) {

        JoinMemberDTO dto = JoinMemberDTO.builder().runningNo(entity.getRunningNo().getNo()).joinNo(entity.getJoinNo())
                .runnerId(entity.getRunnerId().getId()).build(); // JoinMemberDTO 객체 생성

        return dto; // 생성된 DTO 객체 반환
    }

}
