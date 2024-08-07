package com.example.demo.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/*
 * 파일을 특정 폴더에 저장하고 파일 이름을 반환하는 유틸리티 컴포넌트
 */
@Component // 스프링 빈으로 등록
public class FileUtil {

	@Value("${filepath}") // application.properties에서 설정된 파일 저장 경로를 불러온다
	String filepath;

	@Value("${filepath.profile}")
	private String profileFilepath;

	public String profileFileUpload(MultipartFile multipartFile, String id) {
		Path copyOfLocation = null;
		String newFileName = null;
		if(multipartFile.isEmpty()) {
			return null;
		}
		try {
			UUID uuid = UUID.randomUUID();
			newFileName = uuid + StringUtils.cleanPath(multipartFile.getOriginalFilename());
			copyOfLocation = Paths.get(profileFilepath + File.separator + newFileName);

			Files.copy(multipartFile.getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return copyOfLocation.toString();
	}

	public String fileUpload(MultipartFile multipartFile) { // MultipartFile 형태의 파일을 받아 처리하는 메소드
		Path copyOfLocation = null;
		
		if(multipartFile.isEmpty()) { // 전달받은 파일이 비어있으면 메서드를 종료한다
			return null;
		}
		try {
			copyOfLocation = Paths
					.get(filepath + File.separator + StringUtils.cleanPath(multipartFile.getOriginalFilename())); // 저장할 파일의 전체 경로를 생성한다
					// StringUtils.cleanPath로 파일 이름에서 '..' 등의 경로 조작을 방지한다
			
			Files.copy(multipartFile.getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING); // 실제 파일을 위에서 생성한 경로에 저장한다. 이미 파일이 존재하면 대체한다
		} catch (IOException e) { // 파일 처리 중 오류가 발생하면 예외를 캐치한다
			e.printStackTrace();
		}
		// 메소드를 성공적으로 마칠 경우, 원본 파일의 이름을 반환한다.
		return multipartFile.getOriginalFilename();
	}
}