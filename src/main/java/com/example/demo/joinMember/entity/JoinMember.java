package com.example.demo.joinMember.entity;

import com.example.demo.member.entity.Member;
import com.example.demo.runningBoard.entity.Running;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

public class JoinMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int joinNo;

	@ManyToOne
	Running runningNo;

	@ManyToOne
	Member runnerId;

}
