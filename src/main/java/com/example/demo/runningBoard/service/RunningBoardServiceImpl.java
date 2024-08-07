package com.example.demo.runningBoard.service;

import java.util.Optional;
import java.util.Set;

import com.example.demo.diaryBoard.entity.Diary;
import com.example.demo.qnaBoard.entity.Qna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // Spring Data의 페이징 처리를 위한 클래스
import org.springframework.data.domain.PageRequest; // 페이징 요청 정보를 생성하는 클래스
import org.springframework.data.domain.Pageable; // 페이징 처리를 위한 정보를 담는 인터페이스
import org.springframework.data.domain.Sort; // 정렬 기능을 위한 클래스
import org.springframework.stereotype.Service; // 스프링의 서비스 레이어를 나타내는 어노테이션

import com.example.demo.runningBoard.dto.RunningDTO;
import com.example.demo.runningBoard.entity.Running;
import com.example.demo.runningBoard.repository.RunningRepository;

@Service // 이 클래스가 서비스 레이어의 컴포넌트임을 나타냄
public class RunningBoardServiceImpl implements RunningBoardService {

	@Autowired // 스프링의 의존성 주입 기능을 사용하여 RunningRepository 인스턴스를 자동으로 주입
	RunningRepository repository;
	

	@Override
	public int register(RunningDTO dto) {

		Running entity = dtoToEntity(dto); // DTO를 엔티티 객체로 변환
		repository.save(entity); // 변환된 엔티티를 저장
		int newNo = entity.getNo(); // 저장된 엔티티의 번호를 가져옴
		return newNo; // 번호 반환
	}

	@Override
	public Page<RunningDTO> getList(int pageNumber) {
	    // 페이지 번호 조정 (0 기반 인덱스로 조정)
	    int pageNum = (pageNumber == 0) ? 0 : pageNumber - 1;

	    // 페이지 요청 정보 생성, 여기서는 페이지 번호, 페이지당 항목 수, 정렬 기준을 지정
	    Pageable pageable = PageRequest.of(pageNum, 5, Sort.by("no").descending());

	    // repository를 통해 페이지 정보에 맞는 데이터 조회
	    Page<Running> entityPage = repository.findAll(pageable);

	    // 조회된 데이터를 DTO로 변환
	    Page<RunningDTO> dtoPage = entityPage.map(this::entityToDto);

	    return dtoPage; // DTO 페이지 반환
	}

	@Override
	public RunningDTO read(int no) {
		Optional<Running> result = repository.findById(no); // ID를 기준으로 엔티티 조회

		if (result.isPresent()) { // 결과가 존재하면

			Running running = result.get(); // 엔티티 가져오기

			RunningDTO dto = entityToDto(running); // 엔티티를 DTO로 변환

			return dto; // DTO 반환

		}
		return null; // 조회 결과가 없으면 null 반환
	}

	@Override
	public void modify(RunningDTO dto) {

		Optional<Running> result = repository.findById(dto.getNo()); // 수정할 엔티티 조회

		if (result.isPresent()) { // 조회된 엔티티가 있다면

			Running entity = result.get(); // 엔티티 가져오기

			// DTO에서 받은 데이터로 엔티티 수정
			entity.setTitle(dto.getTitle());
			entity.setRunningDate(dto.getRunningDate());
			entity.setLocation(dto.getLocation());
			entity.setContent(dto.getContent());

			repository.save(entity); // 수정된 엔티티 저장
		}

	}

	@Override
	public int remove(int no) {

		Optional<Running> result = repository.findById(no); // 삭제할 엔티티 조회
		if (result.isPresent()) { // 조회된 엔티티가 있다면
			repository.deleteById(no); // 엔티티 삭제

			return 1; // 성공 반환
		} else {
			return 0; // 실패 반환
		}

	}
	@Override
	public Page<RunningDTO> getSearchList(int pageNumber, String keyword) {
	    // 페이지 요청 객체를 생성합니다. 페이지 번호, 페이지 당 항목 수(10), 정렬 기준(번호 내림차순)을 지정합니다.
	    Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("no").descending());

	    // repository의 findByTitleContainingIgnoreCase 메소드를 사용하여,
	    // 제목에 키워드가 포함된(대소문자 구분 없이) Running 엔티티의 페이지를 조회합니다.
	    Page<Running> entityPage = repository.findByTitleContainingIgnoreCase(keyword, pageable);

	    // 조회된 Running 엔티티 페이지를 RunningDTO 페이지로 변환합니다.
	    // entityToDto 메소드를 사용하여 각 엔티티를 DTO로 매핑합니다.
	    Page<RunningDTO> dtoPage = entityPage.map(this::entityToDto);

	    // 변환된 RunningDTO 페이지를 반환합니다.
	    return dtoPage;
	}
	public void addCountView(int no) {
		Optional<Running> result = repository.findById(no);
		if(result.isPresent()){
			Running running = result.get();
			running.setCountView(running.getCountView()+1);
			repository.save(running);
		}
	}
	@Override
	public Running toggleLike(int no, String userId) {
		Optional<Running> result = repository.findById(no);

		if (result.isPresent()) {
			Running running = result.get();
			Set<String> likedUsers = running.getLikedUsers();

			if (likedUsers.contains(userId)) {
				likedUsers.remove(userId);
				running.setCountLike(running.getCountLike() - 1);
			} else {
				likedUsers.add(userId);
				running.setCountLike(running.getCountLike() + 1);
			}

			return repository.save(running);
		}

		return null;
	}
}