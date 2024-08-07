package com.example.demo.joinMember.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.joinMember.dto.JoinMemberDTO;
import com.example.demo.joinMember.service.JoinMemberService;

@RestController // RESTful 웹 서비스의 컨트롤러로 선언합니다.
@RequestMapping("/joinMember") // 이 컨트롤러의 모든 메소드는 '/joinMember' 경로에 매핑됩니다.
public class JoinMemberController {

	@Autowired // Spring에 의해 자동으로 JoinMemberService를 주입받습니다.
	JoinMemberService joinMemberService;

	@PostMapping("/joinRunning")
	public ResponseEntity<String> joinRunning(@RequestParam(name = "runningNo") int runningNo, Principal principal) {
		String runnerId = principal.getName(); // 현재 로그인한 사용자의 ID를 얻습니다.
		
		// 이미 참가한 상태인지 확인합니다.
		if (joinMemberService.isAlreadyJoined(runningNo, runnerId)) {
			// 이미 참가한 상태라면 참가를 취소합니다.
			joinMemberService.cancelJoin(runningNo, runnerId);
			return ResponseEntity.ok("cancelled");
		} else {
			// 그렇지 않으면 참가를 등록합니다.
			JoinMemberDTO joinMemberDTO = joinMemberService.joinRunning(runningNo, runnerId);
			return ResponseEntity.ok("joined");
		}
	}

	@GetMapping("/joinList")
	public ResponseEntity<List<JoinMemberDTO>> getList(@RequestParam(name = "runningNo") int runningNo) {
		List<JoinMemberDTO> list = joinMemberService.getList(runningNo); // 지정된 실행 이벤트의 참가자 목록을 가져옵니다.
		return ResponseEntity.ok(list);
	}

	@DeleteMapping("/cancelJoin")
	public ResponseEntity<Integer> cancelJoin(@RequestParam(name = "runningNo") int runningNo,
			@RequestParam(name = "joinNo") int joinNo, Principal principal) {
		String currentUserId = principal.getName(); // 현재 로그인한 사용자의 이름을 얻습니다.
		int result = joinMemberService.cancelJoin(runningNo, currentUserId); // 참가 취소를 요청하고 결과를 반환합니다.
		return ResponseEntity.ok(result);
	}
}