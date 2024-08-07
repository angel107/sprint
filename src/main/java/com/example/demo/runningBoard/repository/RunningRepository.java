package com.example.demo.runningBoard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.runningBoard.entity.Running;

//Running 엔티티에 대한 JPA 리포지토리 인터페이스 정의
public interface RunningRepository extends JpaRepository<Running, Integer> {

	// 제목에 특정 키워드를 포함하고 있는 Running 엔티티들을 대소문자 구분 없이 검색하여 페이지로 반환하는 메소드
	Page<Running> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}

//findByTitleContainingIgnoreCase 메소드는 스프링 데이터 JPA의 쿼리 메소드 기능을 사용하여, 
//주어진 키워드를 대소문자를 구분하지 않고 제목에 포함하고 있는 Running 엔티티들을 검색하고, 이를 페이징 처리하여 반환합니다. 
//Pageable 객체는 호출하는 쪽에서 페이지 번호, 페이지 당 항목 수, 정렬 순서 등의 페이징 및 정렬 조건을 전달하는 데 사용됩니다.