package com.example.demo.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.comment.entity.mBoardComment;
import com.example.demo.marathonBoard.entity.Marathon;

import feign.Param;
import jakarta.transaction.Transactional;

@Transactional
public interface mBoardCommentRepository extends JpaRepository<mBoardComment, Integer>{

	List<mBoardComment> findByBoard(Marathon board);
	
	void deleteByBoard(Marathon board);
	
	
	
}
