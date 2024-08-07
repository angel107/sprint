package com.example.demo.qnaBoard.repository;

import com.example.demo.diaryBoard.entity.Diary;
import com.example.demo.qnaBoard.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Integer> {
    Page<Qna> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

}
