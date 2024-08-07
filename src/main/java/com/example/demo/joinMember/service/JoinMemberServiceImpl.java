package com.example.demo.joinMember.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.joinMember.dto.JoinMemberDTO;
import com.example.demo.joinMember.entity.JoinMember;
import com.example.demo.joinMember.repository.JoinMemberRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.runningBoard.entity.Running;
import com.example.demo.runningBoard.repository.RunningRepository;

@Service // 이 클래스가 서비스 계층임을 나타내는 어노테이션입니다.
public class JoinMemberServiceImpl implements JoinMemberService {

	@Autowired // JoinMemberRepository에 대한 의존성 주입을 자동으로 합니다.
	JoinMemberRepository joinMemberRepository;
	@Autowired // RunningRepository에 대한 의존성 주입을 자동으로 합니다.
	RunningRepository runningRepository;
	@Autowired // MemberRepository에 대한 의존성 주입을 자동으로 합니다.
	MemberRepository memberRepository;

	@Transactional // 이 메소드가 트랜잭션 범위에서 실행되도록 합니다.
	@Override
	public JoinMemberDTO joinRunning(int runningNo, String runnerId) {

	    Optional<Running> result1 = runningRepository.findById(runningNo); // 주어진 번호로 러닝 정보를 조회합니다.
	    Running running = result1.get(); // Optional에서 Running 인스턴스를 가져옵니다.

	    // runnerId에 해당하는 Member를 찾습니다.
	    Optional<Member> result2 = memberRepository.findById(runnerId); // 주어진 ID로 회원 정보를 조회합니다.
	    Member member = result2.get(); // Optional에서 Member 인스턴스를 가져옵니다.

	    JoinMember joinMember = JoinMember.builder().runningNo(running).runnerId(member).build(); // 러닝 참가 객체를 생성합니다.

	    joinMemberRepository.save(joinMember); // 생성된 러닝 참가 정보를 저장합니다.

	    return entityToDto(joinMember); // 저장된 러닝 참가 정보를 DTO로 변환하여 반환합니다.
	}
	@Transactional // 이 메소드가 트랜잭션 범위에서 실행되도록 합니다.
	@Override
	public List<JoinMemberDTO> getList(int runningNo) {
		List<JoinMember> joinList = joinMemberRepository.findByRunningNo_No(runningNo); // 주어진 러닝 번호에 참여한 목록을 조회합니다.

		return joinList.stream().map(this::entityToDto).collect(Collectors.toList()); // 조회된 목록을 DTO로 변환하여 반환합니다.
	}

	@Transactional // 이 메소드가 트랜잭션 범위에서 실행되도록 합니다.
	@Override
	public int cancelJoin(int runningNo, String runnerId) {
	    // 주어진 참가 번호와 회원 ID에 해당하는 참가 정보를 찾습니다.
	    Optional<JoinMember> result = joinMemberRepository.findByRunningNo_NoAndRunnerId_Id(runningNo, runnerId); // 주어진 조건에 맞는 참가 정보를 조회합니다.

	    if (result.isPresent()) { // 참가 정보가 존재하는 경우
	        joinMemberRepository.delete(result.get()); // 해당 참가 정보를 삭제합니다.
	        return 1; // 취소 성공을 의미하는 1 반환
	    } else {
	        return 0; // 참가 정보가 없는 경우, 취소 실패를 의미하는 0 반환
	    }
	}
	@Override
    public boolean isAlreadyJoined(int runningNo, String runnerId) {
        // 주어진 참가 번호와 회원 ID에 해당하는 참가 정보를 찾습니다.
        Optional<JoinMember> result = joinMemberRepository.findByRunningNo_NoAndRunnerId_Id(runningNo, runnerId); // 주어진 조건에 맞는 참가 정보를 조회합니다.

        // 참가 정보가 존재하면 true를, 그렇지 않으면 false를 반환합니다.
        return result.isPresent(); // 참가 정보의 존재 유무에 따라 boolean 값을 반환합니다.
    }
}
