package com.example.demo.comment.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass // 테이블로 생성하지 않음.
@EntityListeners(value = { AuditingEntityListener.class }) // 엔티티의 변화 감지
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

abstract class BaseEntity {

	@CreatedDate
	LocalDateTime regDate;

	@LastModifiedDate
	LocalDateTime modDate;
}
