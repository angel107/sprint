package com.example.demo.member.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity{

	@Id
	@Column(length = 50)
	String id;
	
	@Column(length = 200, nullable = false)
	String password;
	
	@Column(length = 100, nullable = false)
	String name;
	
	@Column(length = 100, nullable = true)
	String role;	//사용자 등급 추가
	
	private boolean fromSocial;

	String profileImagePath;
}
