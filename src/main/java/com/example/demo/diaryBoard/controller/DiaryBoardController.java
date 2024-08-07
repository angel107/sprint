package com.example.demo.diaryBoard.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.diaryBoard.entity.Diary;
import com.example.demo.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.diaryBoard.dto.DiaryDTO;
import com.example.demo.diaryBoard.service.DiaryBoardService;

@Controller
@RequestMapping("/diaryBoard")
public class DiaryBoardController {

	@Autowired
	DiaryBoardService service;
	@Autowired
	MemberService memberService;
	@GetMapping("/list")
	public void list(@RequestParam(defaultValue = "0", name = "page") int page, Model model) {

		Page<DiaryDTO> list = service.getList(page);
		model.addAttribute("list", list);
	}

	@GetMapping("/register")
	public void register() {

	}

	@PostMapping("/register")
	public String registerPost(DiaryDTO dto, RedirectAttributes redirectAttributes, Principal principal)
			throws Exception {

		String id = principal.getName();
		String name = memberService.findNameById(id);
		dto.setWriter(name);

		int no = service.register(dto);

		redirectAttributes.addFlashAttribute("msg", no);

		return "redirect:/diaryBoard/read?no=" + no;
	}

	@GetMapping("/read")
	public String read(@RequestParam(name = "no") int no, @RequestParam(defaultValue = "0", name = "page") int page,
					   Model model, Principal principal) throws IOException {

		DiaryDTO dto = service.read(no);
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		service.addCountView(no);

		boolean isAuthor = principal != null && dto.getWriter().equals(principal.getName());
		model.addAttribute("isAuthor", isAuthor);

		return "diaryBoard/read";
	}

	@GetMapping("/modify")
	public void modify(@RequestParam(name = "no") int no, Model model) {
		DiaryDTO dto = service.read(no);
		model.addAttribute("dto", dto);
	}

	@PostMapping("/modify")
	public String modifyPost(DiaryDTO dto, RedirectAttributes redirectAttributes) {
		service.modify(dto);
		redirectAttributes.addAttribute("no", dto.getNo());
		return "redirect:/diaryBoard/read";
	}

	@PostMapping("/remove")
	public String removePost(@RequestParam(name = "no") int no) {
		service.remove(no);
		return "redirect:/diaryBoard/list";
	}

	@PostMapping("/toggleLike")
	public ResponseEntity<Map<String, Object>> toggleLike(@RequestParam int no, Principal principal) {
		String userId = principal.getName(); // Principal 객체에서 사용자 이름 가져오기

		Diary diary = service.toggleLike(no, userId);

		Map<String, Object> response = new HashMap<>();
		response.put("liked", diary.getLikedUsers().contains(userId));
		response.put("countLike", diary.getCountLike());

		return ResponseEntity.ok(response);
	}

}