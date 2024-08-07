package com.example.demo.comment.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.member.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.comment.entity.mBoardComment;
import com.example.demo.comment.repository.mBoardCommentRepository;
import com.example.demo.marathonBoard.entity.Marathon;

@Service
public class mBoardCommentServiceImpl implements mBoardCommentService {
	
	@Autowired
	private mBoardCommentRepository repository;

	@Autowired
	private AuthenticationFacade authenticationFacade;
	
	@Override
	public int register(CommentDTO dto) {
		mBoardComment entity = dtoToEntity(dto);
		repository.save(entity);

		return entity.getCommentNo();
	}
	
	@Override
	public List<CommentDTO> getListByBoardNo(int boardNo) {
		Marathon board = Marathon.builder().no(boardNo).build();  //엔티티 생성
		List<mBoardComment> entityList = repository.findByBoard(board);
		List<CommentDTO> dtoList = new ArrayList<>();
		for (mBoardComment entity : entityList) {
			CommentDTO dto = entityToDto(entity);
			dtoList.add(dto);
		}

		return dtoList;
	}


	@Override
	public void remove(int no) {
		mBoardComment comment = repository.findById(no)
				.orElseThrow(()-> new EntityNotFoundException("해당 댓글을 찾지 못했습니다. "));

		Member member = comment.getWriter();

		Authentication authentication = authenticationFacade.getAuthentication();   // 현재 로그인한 사용자 확인
		String writer = member.getId();

		if(!writer.equals(authentication.getName())){
			System.out.println("해당 댓글은 삭제 불가능합니다. (접근 불가능)");
		}else{
			repository.deleteById(no);
		}
	}
}
