package com.example.demo.diaryBoard.dto;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

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
public class DiaryDTO {

	int no;
	String writer;
	String title;
	String content;
	int countLike;
	MultipartFile uploadFile; // 파일 스트림
	String imgPath; // 파일 이름
	LocalDateTime regDate;
	LocalDateTime modDate;
	int countView;
	private Set<String> likedUsers; // 좋아요를 누른 사용자 ID 목록
	private String keyword; // 검색 키워드

}
