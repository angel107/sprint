package com.example.demo.qnaBoard.dto;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaDTO {
    int no;
    String title;
    String writer;
    LocalDateTime regDate;
    LocalDateTime modDate;
    String content;
    int countView;
    int countLike;
    private Set<String> likedUsers; // 좋아요를 누른 사용자 ID 목록

}
