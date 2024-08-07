package com.example.demo.marathonBoard.service;

import com.example.demo.diaryBoard.entity.Diary;
import org.springframework.data.domain.Page;

import com.example.demo.marathonBoard.dto.MarathonDTO;
import com.example.demo.marathonBoard.entity.Marathon;
import com.example.demo.member.entity.Member;

public interface MarathonBoardService {

	int register(MarathonDTO dto) throws Exception;

	Page<MarathonDTO> getList(int pageNumber);

	MarathonDTO read(int no);

	void modify(MarathonDTO dto);

	int remove(int no);

	void addCountView(int no);

	Marathon toggleLike(int no, String userId);

	default Marathon dtoToEntity(MarathonDTO dto) {
		Member member = Member.builder().id(dto.getWriter()).build();
		Marathon entity = Marathon.builder().no(dto.getNo()).writer(member.getId()).title(dto.getTitle())
				.marathonDate(dto.getMarathonDate()).location(dto.getLocation()).content(dto.getContent())
				.countLike(dto.getCountLike()).imgPath(dto.getImgPath()).countView(dto.getCountView()).build();

		return entity;

	}

	default MarathonDTO entityToDto(Marathon entity) {

		MarathonDTO dto = MarathonDTO.builder().no(entity.getNo()).writer(entity.getWriter()).title(entity.getTitle())
				.MarathonDate(entity.getMarathonDate()).location(entity.getLocation()).content(entity.getContent())
				.countLike(entity.getCountLike()).regDate(entity.getRegDate()).modDate(entity.getModDate())
				.imgPath(entity.getImgPath()).countView(entity.getCountView()).build();

		return dto;
	}
}
