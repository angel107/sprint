package com.example.demo.comment.repository;

import com.example.demo.comment.entity.dBoardComment;
import com.example.demo.diaryBoard.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface dBoardCommentRepository extends JpaRepository<dBoardComment, Integer> {

        List<dBoardComment> findByBoard(Diary board);

        void deleteByBoard(Diary board);
    }

