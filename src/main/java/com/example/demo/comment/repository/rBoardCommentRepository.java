package com.example.demo.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.comment.entity.rBoardComment;
import com.example.demo.runningBoard.entity.Running;

import jakarta.transaction.Transactional;

@Transactional
public interface rBoardCommentRepository extends JpaRepository<rBoardComment, Integer>{
	
	List<rBoardComment> findByBoard(Running board);
	
	void deleteByBoard(Running board);
	
	
	
}
