package com.example.demo.runningBoard.entity;



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
//@Entity 어노테이션은 이 클래스가 JPA 엔티티임을 나타냅니다.
@Entity
//@Table 어노테이션은 이 엔티티가 매핑될 테이블 정보를 지정합니다. 여기서는 기본 테이블 이름을 사용합니다.
@Table

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Running extends BaseEntity {

	@Id // 엔티티의 기본 키임을 나타냅니다.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임합니다. (즉, auto_increment)
	int no;

	@Column(length = 20, nullable = false)
	String writer;

	@Column(length = 100, nullable = false)
	String title;

	@Column(length = 30, nullable = false)
	LocalDateTime runningDate;

	@Column(length = 30, nullable = false)
	String location;

	@Column(length = 255, nullable = false)
	String content;
	
	@Column(length = 30, nullable = true)
	double latitude;
	
	@Column(length = 30, nullable = true)
	double longtitude;

	@Column(length = 10, nullable = true)
	int countLike;
	
	@Column(length = 20, nullable = true)
	String keyword;
	//파일첨부 필요없음
	@Column(length = 255, nullable = false)
	int countView;

	@ElementCollection
	@CollectionTable(name = "running_liked_users", joinColumns = @JoinColumn(name = "running_no"))
	@Column(name = "user_id")
	private Set<String> likedUsers = new HashSet<>();
}
