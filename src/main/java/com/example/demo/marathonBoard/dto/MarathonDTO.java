package com.example.demo.marathonBoard.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
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
public class MarathonDTO {
	int no;
	String writer;
	String title;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDateTime MarathonDate;
	String location;
	String content;
	int countLike;
	int countView;
	MultipartFile uploadFile; // 파일 스트림
	String imgPath; // 파일 이름
	LocalDateTime regDate;
	LocalDateTime modDate;

}
