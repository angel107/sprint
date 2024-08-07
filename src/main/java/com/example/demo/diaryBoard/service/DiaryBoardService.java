package com.example.demo.diaryBoard.service;

import org.springframework.data.domain.Page;

import com.example.demo.diaryBoard.dto.DiaryDTO;
import com.example.demo.diaryBoard.entity.Diary;
import com.example.demo.member.entity.Member;

public interface DiaryBoardService {

	int register(DiaryDTO dto);

	Page<DiaryDTO> getList(int pageNumber);

	DiaryDTO read(int no);

	void modify(DiaryDTO dto);

	int remove(int no);

	Page<DiaryDTO> getSearchList(int pageNumber, String keyword);

	void addCountView(int no);

	Diary toggleLike(int no, String userId);


	default Diary dtoToEntity(DiaryDTO dto) {
		Member member = Member.builder().id(dto.getWriter()).build();

		Diary entity = Diary.builder()
				.no(dto.getNo())
				.writer(member.getId())
				.title(dto.getTitle())
				.content(dto.getContent())
				.countLike(dto.getCountLike())
				.imgPath(dto.getImgPath())
				.countView(dto.getCountView())
				.likedUsers(dto.getLikedUsers())
				.build();

		return entity;
	}

	default DiaryDTO entityToDto(Diary entity) {
		DiaryDTO dto = DiaryDTO.builder()
				.no(entity.getNo())
				.writer(entity.getWriter())
				.title(entity.getTitle())
				.content(entity.getContent())
				.countLike(entity.getCountLike())
				.imgPath(entity.getImgPath())
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.countView(entity.getCountView())
				.likedUsers(entity.getLikedUsers())
				.build();

		return dto;
	}

}