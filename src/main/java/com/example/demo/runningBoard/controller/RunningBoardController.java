package com.example.demo.runningBoard.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.runningBoard.entity.Running;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.member.service.MemberService;
import com.example.demo.runningBoard.dto.RunningDTO;
import com.example.demo.runningBoard.service.RunningBoardService;
import com.example.demo.runningBoard.service.RunningBoardServiceImpl;
import com.example.demo.weatherApi.dto.WeatherDTO;
import com.example.demo.weatherApi.service.WeatherService;

@Controller // 스프링 MVC에서 컨트롤러임을 나타냄
@RequestMapping("/runningBoard") // 이 컨트롤러의 모든 매핑은 /runningBoard로 시작
public class RunningBoardController {
	@Autowired // 스프링이 자동으로 해당 타입의 빈을 연결
	RunningBoardServiceImpl service2;
	@Autowired
	RunningBoardService service;

	@Autowired
	MemberService memberService;

	@Autowired
	WeatherService weatherService;

	@GetMapping("/list")
	public void list(@RequestParam(defaultValue = "0", name = "page") int page, Model model) {
		Page<RunningDTO> list = service.getList(page); // 페이지 번호에 해당하는 목록을 가져옴
		model.addAttribute("list", list); // 모델에 목록 추가

	}

	@GetMapping("/search")
	public String search(@RequestParam(defaultValue = "0", name = "page") int page,
						 @RequestParam(name = "keyword") String keyword, Model model) {
		Page<RunningDTO> list = service.getSearchList(page, keyword); // 검색어로 필터링된 목록을 가져옴
		model.addAttribute("list", list); // 모델에 목록 추가
		return "/runningBoard/list"; // 목록 페이지로 리다이렉트
	}

	@GetMapping("/register")
	public void register() {
		// 글 등록 페이지를 보여줌
	}

	@PostMapping("/register")
	public String registerPost(RunningDTO dto, RedirectAttributes redirectAttributes, Principal principal) {

		String id = principal.getName(); // 현재 사용자의 ID를 가져옴

		String name = memberService.findNameById(id); // ID로 사용자 이름을 찾음

		dto.setWriter(name); // DTO에 작성자 설정

		int no = service.register(dto); // 글 등록 후 글 번호를 받음

		redirectAttributes.addFlashAttribute("msg", no); // 리다이렉트 후 한 번만 사용될 데이터를 전달

		return "redirect:/runningBoard/read?no=" + no; // 등록된 글 읽기 페이지로 리다이렉트
	}

	@GetMapping("/read")
	public String read(@RequestParam(name = "no") int no, @RequestParam(defaultValue = "0", name = "page") int page,
					   Model model, Principal principal) throws IOException {
		RunningDTO dto = service.read(no);
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);

		List<WeatherDTO> weather = weatherService.entityToDto();
		model.addAttribute("weather", weather);
		service.addCountView(no);

		// 현재 로그인한 사용자와 글 작성자 비교
		boolean isAuthor = principal != null && dto.getWriter().equals(principal.getName());
		model.addAttribute("isAuthor", isAuthor);

		return "runningBoard/read";
	}

	@GetMapping("/modify")
	public void modify(@RequestParam(name = "no") int no, Model model) {
		RunningDTO dto = service.read(no); // 수정할 글의 정보를 가져옴
		model.addAttribute("dto", dto); // 모델에 글 정보 추가
	}

	@PostMapping("/modify")
	public String modifyPost(RunningDTO dto, RedirectAttributes redirectAttributes) {
		service.modify(dto); // 글 정보를 수정
		redirectAttributes.addAttribute("no", dto.getNo()); // 수정된 글 번호를 파라미터로 추가
		return "redirect:/runningBoard/read"; // 수정된 글 읽기 페이지로 리다이렉트
	}

	@PostMapping("/remove")
	public String removePost(@RequestParam(name = "no") int no) {
		service.remove(no); // 글 번호로 글을 삭제
		return "redirect:/runningBoard/list";
	}

	@PostMapping("/toggleLike")
	public ResponseEntity<Map<String, Object>> toggleLike(@RequestParam int no, Principal principal) {
		String userId = principal.getName(); // Principal 객체에서 사용자 이름 가져오기

		Running running = service.toggleLike(no, userId);

		Map<String, Object> response = new HashMap<>();
		response.put("liked", running.getLikedUsers().contains(userId));
		response.put("countLike", running.getCountLike());

		return ResponseEntity.ok(response);
	}


}