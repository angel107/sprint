package com.example.demo.marathonBoard.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Marathon extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int no;

	@Column(length = 100, nullable = false)
	String title;
	
	@Column(length = 40, nullable = false)
	String writer;
	
	@Column(length = 30, nullable = false)
	LocalDateTime marathonDate;
	
	@Column(length = 30, nullable = false)
	String location;

	@Column(length = 255, nullable = false)
	String content;

	@Column(length = 10, nullable = true)
	int countLike;
	
	@Column(length = 200, nullable = true)
	private String imgPath; //첨부파일 이름

	@Column(length = 255, nullable = false)
	int countView;

	@ElementCollection
	@CollectionTable(name = "marathon_liked_users", joinColumns = @JoinColumn(name = "marathon_no"))
	@Column(name = "user_id")
	private Set<String> likedUsers = new HashSet<>();
}
