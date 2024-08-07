package com.example.demo.joinMember.dto;

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


public class JoinMemberDTO {

	int runningNo;
	int joinNo;
	String runnerId;
}
