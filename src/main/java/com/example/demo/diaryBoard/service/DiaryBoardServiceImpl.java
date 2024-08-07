package com.example.demo.diaryBoard.service;

import java.util.Optional;
import java.util.Set;

import com.example.demo.marathonBoard.entity.Marathon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.diaryBoard.dto.DiaryDTO;
import com.example.demo.diaryBoard.entity.Diary;
import com.example.demo.diaryBoard.repository.DiaryRepository;
import com.example.demo.util.FileUtil;

@Service
public class DiaryBoardServiceImpl implements DiaryBoardService {

	@Autowired
	DiaryRepository repository;

	@Autowired
	private FileUtil fileUtil;

	@Override
	public int register(DiaryDTO dto) {

		Diary entity = dtoToEntity(dto);

		// 파일을 폴더에 저장하고 파일이름 반환
		String imgPath = fileUtil.fileUpload(dto.getUploadFile());
		// 엔티티에 파일이름 저장
		entity.setImgPath(imgPath);

		repository.save(entity);
		int newNo = entity.getNo();

		return newNo; // 새로운 게시물의 번호 반환
	}

	@Override
	public Page<DiaryDTO> getList(int pageNumber) {

		int pageNum = (pageNumber == 0) ? 0 : pageNumber - 1;

		Pageable pageable = PageRequest.of(pageNum, 10, Sort.by("no").descending());

		Page<Diary> entityPage = repository.findAll(pageable);

		Page<DiaryDTO> dtoPage = entityPage.map(entity -> entityToDto(entity));
		return dtoPage;
	}

	@Override
	public DiaryDTO read(int no) {
		Optional<Diary> result = repository.findById(no);

		if (result.isPresent()) {
			Diary diary = result.get();
			DiaryDTO dto = entityToDto(diary);

			return dto;
		}
		return null;
	}

	@Override
	public void modify(DiaryDTO dto) {
		Optional<Diary> result = repository.findById(dto.getNo());

		if (result.isPresent()) {
			Diary entity = result.get();

			entity.setTitle(dto.getTitle());
			entity.setContent(dto.getContent());
			entity.setImgPath(fileUtil.fileUpload(dto.getUploadFile()));

			repository.save(entity);
		}
	}

	@Override
	public int remove(int no) {

		Optional<Diary> result = repository.findById(no);
		if (result.isPresent()) {
			repository.deleteById(no);

			return 1; // 삭제 성공
		} else {
			return 0; // 삭제 실패
		}

	}

	@Override
	public Page<DiaryDTO> getSearchList(int pageNumber, String keyword) {
		Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("no").descending());

		Page<Diary> entityPage = repository.findByTitleContainingIgnoreCase(keyword, pageable);

		Page<DiaryDTO> dtoPage = entityPage.map(this::entityToDto);
		return dtoPage;
	}

	public void addCountView(int no) {
		Optional<Diary> result = repository.findById(no);
		if(result.isPresent()){
			Diary diary = result.get();
			diary.setCountView(diary.getCountView()+1);
			repository.save(diary);
		}
	}
	@Override
	public Diary toggleLike(int no, String userId) {
		Optional<Diary> result = repository.findById(no);

		if (result.isPresent()) {
			Diary diary = result.get();
			Set<String> likedUsers = diary.getLikedUsers();

			if (likedUsers.contains(userId)) {
				likedUsers.remove(userId);
				diary.setCountLike(diary.getCountLike() - 1);
			} else {
				likedUsers.add(userId);
				diary.setCountLike(diary.getCountLike() + 1);
			}

			return repository.save(diary);
		}

		return null;
	}

}
