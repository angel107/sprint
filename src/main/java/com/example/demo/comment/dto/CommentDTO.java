package com.example.demo.comment.dto;

import java.time.LocalDateTime;

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
public class CommentDTO {
	
	 
	int commentNo;	//댓글번호
	
	int boardNo;	//게시물번호
	
	String content;	//댓글내용
	
	String writer;	//작성자
	
	LocalDateTime regDate;	//등록일자
	
	LocalDateTime modDate;	//수정일자
	
}
