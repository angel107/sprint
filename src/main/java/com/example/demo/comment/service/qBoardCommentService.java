package com.example.demo.comment.service;

import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.comment.entity.qBoardComment;
import com.example.demo.member.entity.Member;
import com.example.demo.qnaBoard.entity.Qna;

import java.util.*;

public interface qBoardCommentService {
    int register(CommentDTO dto);

    List<CommentDTO> getListByBoardNo(int boardNo);

    void remove(int no);

    default qBoardComment dtoToEntity(CommentDTO dto) {
        Member member = Member.builder().id(dto.getWriter()).build();
        Qna qna = Qna.builder().no(dto.getBoardNo()).build();
        qBoardComment entity = qBoardComment.builder()
                .commentNo(dto.getCommentNo())
                .board(qna)
                .content(dto.getContent())
                .writer(member)
                .build();
        return entity;
    }

    default CommentDTO entityToDto(qBoardComment entity) {
        CommentDTO dto = CommentDTO.builder()
                .commentNo(entity.getCommentNo())
                .boardNo(entity.getBoard().getNo())
                .content(entity.getContent())
                .writer(entity.getWriter().getId())
                .regDate(entity.getRegDate()) // 등록 날짜
                .modDate(entity.getModDate()) // 수정 날짜
                .build();

        return dto;
    }
}
