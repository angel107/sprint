package com.example.demo.comment.controller;

import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.comment.service.qBoardCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/qBoardComment")
public class qBoardCommentController {

    @Autowired
    qBoardCommentService service;

    @ResponseBody
    @GetMapping("/list")
    public List<CommentDTO> list(@RequestParam(name = "boardNo") int boardNo) {
        List<CommentDTO> commentList = service.getListByBoardNo(boardNo);
        return commentList;
    }

    @ResponseBody
    @PostMapping("/register")
    public HashMap<String, Boolean> register(CommentDTO dto, Principal principal) {

        HashMap<String, Boolean> map = new HashMap<>();
        String id = principal.getName();
        dto.setWriter(id);
        service.register(dto);
        map.put("success", true);
        return map;

    }

    @ResponseBody
    @GetMapping("/remove")
    public HashMap<String,Boolean> remove(@RequestParam(name = "commentNo") int commentNo) {
        HashMap<String,Boolean> map = new HashMap<>();
        service.remove(commentNo);
        map.put("success", true);
        return map;
    }


}
