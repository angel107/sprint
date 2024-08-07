package com.example.demo.comment.entity;

import com.example.demo.member.entity.Member;
import com.example.demo.runningBoard.entity.Running;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class rBoardComment extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int commentNo;
	
	@ManyToOne
	Running board;	//연관 관계 설정
	
	@Column(length = 1500)
	String content;
	
	@ManyToOne
	Member writer;	//연관 관계 설정
}
