package com.example.demo.runningBoard.service;

import org.springframework.data.domain.Page;

import com.example.demo.member.entity.Member;
import com.example.demo.runningBoard.dto.RunningDTO;
import com.example.demo.runningBoard.entity.Running;

public interface RunningBoardService {

	// RunningDTO를 사용하여 새로운 러닝 게시물을 등록하고, 등록된 게시물의 ID를 반환합니다.
	int register(RunningDTO dto);
	
	// 주어진 페이지 번호에 해당하는 러닝 게시물 목록을 Page<RunningDTO> 형태로 반환합니다.
	Page<RunningDTO> getList(int pageNumber);
	
	// 주어진 번호(no)에 해당하는 러닝 게시물의 상세 정보를 RunningDTO로 반환합니다.
	RunningDTO read(int no);
	
	// RunningDTO의 정보를 사용하여 기존의 러닝 게시물을 수정합니다.
	void modify(RunningDTO dto);
	
	// 주어진 번호(no)에 해당하는 러닝 게시물을 삭제하고, 삭제된 게시물의 ID를 반환합니다.
	int remove(int no);

	void addCountView(int no);

	Running toggleLike(int no, String userId);

	// 주어진 키워드로 검색된 러닝 게시물 목록을 Page<RunningDTO> 형태로 반환합니다. 검색은 페이지 번호에 따라 페이징됩니다.
	Page<RunningDTO> getSearchList(int pageNumber, String keyword);
	
	// RunningDTO를 Running 엔티티 객체로 변환합니다. 이 과정에서 Member 엔티티도 조회하여 설정해야 합니다.
	default Running dtoToEntity(RunningDTO dto) {

		
		Member member = Member.builder().id(dto.getWriter()).build();

		Running entity = Running.builder().no(dto.getNo()).writer(member.getId()).title(dto.getTitle())
				.runningDate(dto.getRunningDate()).location(dto.getLocation()).content(dto.getContent())
				.latitude(dto.getLatitude()).longtitude(dto.getLongtitude()).countLike(dto.getCountLike())
				.countView(dto.getCountView()).build();

		return entity;

	}
	
	// Running 엔티티 객체를 RunningDTO로 변환합니다. 이 과정에서 Member 엔티티도 조회하여 설정해야 합니다.
	default RunningDTO entityToDto(Running entity) {
		

		Member member = Member.builder().id(entity.getWriter()).build();

		RunningDTO dto = RunningDTO.builder().no(entity.getNo()).writer(entity.getWriter()).title(entity.getTitle())
				.runningDate(entity.getRunningDate()).location(entity.getLocation()).content(entity.getContent())
				.latitude(entity.getLatitude()).longtitude(entity.getLongtitude()).countLike(entity.getCountLike())
				.regDate(entity.getRegDate()).modDate(entity.getModDate()).countView(entity.getCountView()).build();

		return dto;
	}

}