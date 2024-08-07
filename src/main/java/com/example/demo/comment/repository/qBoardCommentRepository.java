package com.example.demo.comment.repository;

import com.example.demo.comment.entity.qBoardComment;
import com.example.demo.qnaBoard.entity.Qna;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Transactional
public interface qBoardCommentRepository extends JpaRepository<qBoardComment, Integer> {
    List<qBoardComment> findByBoard(Qna board);
}
