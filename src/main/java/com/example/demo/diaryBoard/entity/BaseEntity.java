package com.example.demo.diaryBoard.entity;

import java.time.LocalDateTime;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass // 테이블로 생성하지 않음.
@EntityListeners(value = { AuditingEntityListener.class }) // 엔티티의 변화 감지
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity { 

	@CreatedDate
	LocalDateTime regDate;

	@LastModifiedDate
	LocalDateTime modDate;
}
