package com.example.demo.qnaBoard.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

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
