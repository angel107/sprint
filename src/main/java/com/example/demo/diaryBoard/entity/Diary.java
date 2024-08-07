package com.example.demo.diaryBoard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Diary extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int no;

	@Column(length = 100, nullable = false)
	String title;

	@Column(length = 20, nullable = false)
	String writer;

	@Column(length = 255, nullable = false)
	String content;

	@Column(length = 10, nullable = true)
	int countLike;

	@Column(length = 200, nullable = true)
	private String imgPath; // 첨부파일 이름

	@Column(length = 255, nullable = false)
	int countView;

	@ElementCollection
	@CollectionTable(name = "diary_liked_users", joinColumns = @JoinColumn(name = "diary_no"))
	@Column(name = "user_id")
	private Set<String> likedUsers = new HashSet<>();

}