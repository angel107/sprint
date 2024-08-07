package com.example.demo.comment.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.comment.service.rBoardCommentService;


@Controller
@RequestMapping("/rBoardcomment")
public class rBoardCommentController {

    //rBoardCommentService 의존성 주입
	@Autowired
	rBoardCommentService service;
	
    //댓글 목록 조회 처리
	@ResponseBody
	@GetMapping("/list")
	public List<CommentDTO> list(@RequestParam(name = "boardNo") int boardNo) {
	    //서비스 레이어를 통해 특정 게시판 번호에 해당하는 댓글 목록 조회
		List<CommentDTO> commentlist = service.getListByBoardNo(boardNo);
		
		//조회된 댓글 목록 반환
		return commentlist;
		
	}
	
    //댓글 등록 처리
	@ResponseBody
	@PostMapping("/register")
	public HashMap<String,Boolean> register(CommentDTO dto, Principal principal) {
		
		//응답으로 반환할 맵 객체 생성
		HashMap<String,Boolean> map = new HashMap<>();
		
		//현재 로그인한 사용자의 아이디를 임시 방편으로 설정
		String id = principal.getName(); //시큐리티를 통해 접근한 사용자의 ID를 확득
		
		dto.setWriter(id);
		
		//DTO 정보를 서비스 레이어에 전달하여 댓글 등록 처리
		service.register(dto);
		
		//처리 결과를 맵에 추가
		map.put("success", true);
		
		//처리 결과 반환
		return map;
	}
	
    //댓글 삭제 처리
	@ResponseBody
	@GetMapping("/remove")
	public HashMap<String,Boolean> remove(@RequestParam(name = "commentNo") int commentNo) {
	    //응답으로 반환할 맵 객체 생성
		HashMap<String,Boolean> map = new HashMap<>();
		
		//서비스 레이어를 통해 댓글 삭제 처리
		service.remove(commentNo);
		
		//처리 결과를 맵에 추가
		map.put("success", true);
		
		//처리 결과 반환
		return map;
	}
	
	
}

