package com.example.demo.marathonBoard.service;

import java.util.Optional;
import java.util.Set;

import com.example.demo.diaryBoard.entity.Diary;
import com.example.demo.runningBoard.entity.Running;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.marathonBoard.dto.MarathonDTO;
import com.example.demo.marathonBoard.entity.Marathon;
import com.example.demo.marathonBoard.repository.MarathonRepository;
import com.example.demo.util.FileUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MarathonBoardServiceImpl implements MarathonBoardService {

	@Autowired
	MarathonRepository repository;

	@Autowired
	private FileUtil fileUtil;

	@Override
	public int register(MarathonDTO dto) {

		Marathon entity = dtoToEntity(dto); // 파라미터로 전달받은 dto를 엔티티로 변환

		// 유틸클래스를 이용해서 파일을 폴더에 저장하고 파일이름을 반환받는다
		String imgPath = fileUtil.fileUpload(dto.getUploadFile());
		// 그리고 엔티티에 파일이름을 저장한다
		entity.setImgPath(imgPath);

		repository.save(entity); // 리파지토리로 게시물 등록
		int newNo = entity.getNo();

		return newNo; // 새로운 게시물의 번호 반환

//		String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
//		UUID uuid = UUID.randomUUID();
//		String fileName = uuid + "_" + file.getOriginalFilename();
//
//		File saveFile = new File(projectPath, fileName);
//		file.transferTo(saveFile);
//
//		Marathon entity = dtoToEntity(dto);
//
//		entity.setFileName(fileName); // 파일명 변경
//		entity.setFilePath("/files/" + fileName);
//
//		repository.save(entity);
//		int newNo = entity.getNo();
//		return newNo;
	}

	@Override
	public Page<MarathonDTO> getList(int pageNumber) {

		int pageNum = (pageNumber == 0) ? 0 : pageNumber - 1;

		Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("no").descending());

		Page<Marathon> entityPage = repository.findAll(pageable);

		Page<MarathonDTO> dtoPage = entityPage.map(entity -> entityToDto(entity));

		return dtoPage;
	}

	@Override
	public MarathonDTO read(int no) {
		Optional<Marathon> result = repository.findById(no);

		if (result.isPresent()) {

			Marathon Marathon = result.get();

			MarathonDTO dto = entityToDto(Marathon);

			return dto;

		}
		return null;
	}

	@Override
	public void modify(MarathonDTO dto) {

		Optional<Marathon> result = repository.findById(dto.getNo());

		if (result.isPresent()) {

			Marathon entity = result.get();

			entity.setTitle(dto.getTitle());
			entity.setMarathonDate(dto.getMarathonDate());
			entity.setLocation(dto.getLocation());
			entity.setContent(dto.getContent());
			entity.setImgPath(fileUtil.fileUpload(dto.getUploadFile()));

			repository.save(entity);
		}

	}

	@Override
	public int remove(int no) {

		Optional<Marathon> result = repository.findById(no);
		if (result.isPresent()) {
			repository.deleteById(no);

			return 1; // 성공
		} else {
			return 0; // 실패
		}

	}

	public void addCountView(int no) {
		Optional<Marathon> result = repository.findById(no);
		if(result.isPresent()){
			Marathon marathon = result.get();
			marathon.setCountView(marathon.getCountView()+1);
			repository.save(marathon);
		}
	}

	@Override
	public Marathon toggleLike(int no, String userId) {
		Optional<Marathon> result = repository.findById(no);

		if (result.isPresent()) {
			Marathon marathon = result.get();
			Set<String> likedUsers = marathon.getLikedUsers();

			if (likedUsers.contains(userId)) {
				likedUsers.remove(userId);
				marathon.setCountLike(marathon.getCountLike() - 1);
			} else {
				likedUsers.add(userId);
				marathon.setCountLike(marathon.getCountLike() + 1);
			}

			return repository.save(marathon);
		}

		return null;
	}
}
