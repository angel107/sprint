package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication // 이 애너테이션으로 인해 스프링 부트의 자동 설정, 스프링 빈 읽기와 생성을 모두 자동으로 설정됩니다.
@EnableScheduling      // 스프링에서 제공하는 스케줄링 기능을 활성화합니다.
@EnableJpaAuditing     // JPA Auditing을 활성화합니다. JPA Auditing은 엔티티가 생성/수정되었을 때 자동으로 시간을 기록해주는 기능입니다.
public class ProjectSprintApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectSprintApplication.class, args); // 스프링 애플리케이션을 시작하는 부트스트래핑 메서드입니다. 내부적으로 서버를 구동하며, 애플리케이션을 시작합니다.
	}

}