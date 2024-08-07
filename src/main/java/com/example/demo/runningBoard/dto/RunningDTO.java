package com.example.demo.runningBoard.dto;


import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RunningDTO {

	int no;
	String writer;
	String title;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime runningDate;
	String location;
	String content;
	double latitude;
	double longtitude;
	int countLike;
	int countView;
	LocalDateTime regDate;
	LocalDateTime modDate;
	private Set<String> likedUsers; // 좋아요를 누른 사용자 ID 목록

	private String keyword; // 검색 키워드
}
