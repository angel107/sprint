package com.example.demo.comment.service;

import java.util.List;

import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.comment.entity.mBoardComment;
import com.example.demo.marathonBoard.entity.Marathon;
import com.example.demo.member.entity.Member;

public interface mBoardCommentService {

	int register(CommentDTO dto);
	
	List<CommentDTO> getListByBoardNo(int BoarNO);
	
	void remove(int no);
	
	default mBoardComment dtoToEntity(CommentDTO dto) {

		Member member = Member.builder().id(dto.getWriter()).build(); 
		
		Marathon board = Marathon.builder().no(dto.getBoardNo()).build();  

		mBoardComment entity = mBoardComment.builder()
				.commentNo(dto.getCommentNo())
				.board(board)
				.content(dto.getContent())
				.writer(member)
				.build();
		
		return entity;
	}

	default CommentDTO entityToDto(mBoardComment entity) {

		CommentDTO dto = CommentDTO.builder()
				.commentNo(entity.getCommentNo())
				.boardNo(entity.getBoard().getNo())
				.content(entity.getContent())
				.writer(entity.getWriter().getId())
				.regDate(entity.getRegDate()) 
				.modDate(entity.getModDate())
				.build();

		return dto;
	}
	
}
