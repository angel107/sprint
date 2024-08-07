package com.example.demo.home;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class HomeController {

	@Autowired
	MemberService memberService;

	@GetMapping("/")
	public String home(Model model) {
		// 현재 로그인한 사용자의 정보를 가져옵니다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserId = authentication.getName();

		// 현재 로그인한 사용자의 정보를 서비스를 통해 가져옵니다.
		MemberDTO loggedInUser = memberService.findMemberById(loggedInUserId);

		// 모델에 현재 로그인한 사용자의 정보를 추가합니다.
		model.addAttribute("loggedInUser", loggedInUser);

		return "home/main";
	}

	/* 로그인 페이지와 에러 메시지 처리 */
	@GetMapping("/customlogin")
	public String customLogin(@RequestParam(value = "error", required = false) String error,
							  @RequestParam(value = "exception", required = false) String exception,
							  Model model) {
		// 에러와 예외가 있을 경우 모델에 추가합니다.
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "home/login";
	}
}
