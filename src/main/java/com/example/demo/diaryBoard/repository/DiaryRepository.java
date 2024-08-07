package com.example.demo.diaryBoard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.diaryBoard.entity.Diary;
import com.example.demo.runningBoard.entity.Running;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {

	Page<Diary> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
