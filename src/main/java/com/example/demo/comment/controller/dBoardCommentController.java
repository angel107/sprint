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
import com.example.demo.comment.service.dBoardCommentService;

@Controller
@RequestMapping("/dBoardComment")
public class dBoardCommentController {

    @Autowired
    dBoardCommentService service;

    @ResponseBody
    @GetMapping("/list")
    public List<CommentDTO> list(@RequestParam(name = "boardNo") int boardNo) {

        List<CommentDTO> commentList = service.getListByBoardNo(boardNo);

        return commentList;
    }

    @ResponseBody
    @PostMapping("/register")
    public HashMap<String, Boolean> register(CommentDTO dto, Principal principal) {

        // 맵 객체 생성
        HashMap<String, Boolean> map = new HashMap<>();

        // 아이디 저장
        String id = principal.getName();

        dto.setWriter(id);

        // dto정보를 service에 저장
        service.register(dto);

        // 처리결과 반환
        map.put("success", true);

        return map;

    }

    @ResponseBody
    @GetMapping("/remove")
    public HashMap<String, Boolean> remove(@RequestParam(name = "commentNo") int commentNo) {
        HashMap<String, Boolean> map = new HashMap<>();
        service.remove(commentNo);
        map.put("success", true);
        return map;
    }

}