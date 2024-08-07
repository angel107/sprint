package com.example.demo.member.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.service.MemberService;


@Controller
public class MemberController {

	@Autowired
	private MemberService service; // memberService를 자동 연결해줍니다. 이 서비스는 회원 관련 비즈니스 로직을 처리합니다.

	@GetMapping("/member/list") 
	public void list(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Page<MemberDTO> list = service.getList(page); // 요청된 페이지 번호에 해당하는 회원 목록을 가져옵니다.
		model.addAttribute("list", list); // 가져온 회원 목록을 모델에 추가합니다. 이 데이터는 뷰에서 사용됩니다.
	}

	@GetMapping("/register")
	public String register() { 
		return "member/register"; // 회원 등록 페이지를 반환합니다. 여기서 "member/register"는 뷰의 경로를 나타냅니다.
	}

	@PostMapping("/register")
	public String registerPost(MemberDTO dto, RedirectAttributes redirectAttributes) {

		boolean isSuccess = service.register(dto); // 입력 받은 MemberDTO를 사용하여 회원 등록을 시도합니다.

		if(isSuccess) {
			return "redirect:/"; // 등록이 성공하면, 홈 페이지로 리다이렉트합니다.
		}else {
			redirectAttributes.addFlashAttribute("msg", "아이디가 중복되어 등록에 실패하였습니다"); // 실패 시 메시지를 전달합니다.
			return "redirect:/register"; // 등록 실패 시, 등록 페이지로 다시 리다이렉트합니다.
		}

	}

	@GetMapping("/member/read")
	public void read(@RequestParam(name = "id") String id, @RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		MemberDTO dto = service.read(id); // 특정 id를 가진 회원의 정보를 읽어옵니다.
		model.addAttribute("dto", dto); // 읽어온 회원 정보를 모델에 추가합니다.
		model.addAttribute("page", page); // 현재 페이지 번호도 모델에 추가합니다. 목록으로 돌아갈 때 사용됩니다.
	}

	@GetMapping("/member/modify")
	public void modify(@RequestParam(name = "id") String id, Model model) {
		MemberDTO dto = service.read(id); // 수정하고자 하는 회원의 정보를 읽어와서
		model.addAttribute("dto", dto); // 그 정보를 모델에 추가합니다. 수정 폼에서 기존 값으로 표시됩니다.
	}

	@PostMapping("/member/modify")
	public String modifyPost(MemberDTO memberDTO, RedirectAttributes redirectAttributes) {
		service.modify(memberDTO); // 입력받은 MemberDTO로 회원 정보를 수정합니다.
		redirectAttributes.addAttribute("id", memberDTO.getId()); // 수정된 회원의 ID를 연이어 조회할 수 있도록 리다이렉트 속성에 추가합니다.
		return "redirect:/member/read"; // 회원의 상세 정보 페이지로 리다이렉트합니다.
	}
}
