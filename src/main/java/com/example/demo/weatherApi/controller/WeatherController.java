package com.example.demo.weatherApi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.runningBoard.repository.RunningRepository;
import com.example.demo.weatherApi.dto.WeatherDTO;
import com.example.demo.weatherApi.repository.WeatherRepository;
import com.example.demo.weatherApi.service.WeatherService;

@RestController
@RequestMapping("/runningBoard")
public class WeatherController {

	@Autowired
	WeatherService service;

	@Autowired
	RunningRepository runningRepository;

	@Autowired
	WeatherRepository weatherRepository;

	@GetMapping("/weather")
	public List<WeatherDTO> getWeather( Model model) throws IOException {

//		Running running = runningRepository.findById(running)
//				.orElseThrow(() -> new IllegalArgumentException("해당 Running Entity가 존재하지 않습니다."));

		List<WeatherDTO> list = service.entityToDto();
		model.addAttribute("list", list);
		return list;
	}

//	@GetMapping("/weather/{runningNo}")
//	public ResponseEntity<List<WeatherDTO>> getWeather(@PathVariable int runningNo) throws IOException {
//
//		Running running = runningRepository.findById(runningNo)
//				.orElseThrow(() -> new IllegalArgumentException("해당 Running Entity가 존재하지 않습니다.")); // Running Entity 조회
//		String location = running.getLocation();
//		String code = locationToCode(location);
//
//		Root root = service.jsonToRoot(code);
//		List<WeatherDTO> list = new ArrayList<>();
//
//		if (root != null && root.getResponse() != null && root.getResponse().getBody() != null
//				&& root.getResponse().getBody().getItems() != null) {
//			// WeatherDTO 구성
//			for (Item item : root.getResponse().getBody().getItems().getItem()) {
//				WeatherDTO dto = WeatherDTO.builder().when(item.numEf).temperature(item.getTa()).weather(item.getWf())
//						.rainPossi(item.getRnSt()).build();
//				list.add(dto);
//			}
//			return new ResponseEntity<>(list, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//	} // ResponseEntity를 통해 클라이언트 측에서 에러 응답 및 입력 검증 가능
//
//	@ExceptionHandler(IOException.class)
//	public ResponseEntity<String> handleIOException(IOException e) {
//		return new ResponseEntity<>("Failed to get weather data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//	
//	
//	//위치 변수를 입력 받아 코드로 변환하는 메소드(백업)
//	public String locationToCode(String location) {
//		if (location.contains("서울")) {
//			return "11B10101";
//		} else if (location.contains("인천")) {
//			return "11B20201";
//		} else if (location.contains("부산")) {
//			return "11H20201";
//		}
//		// ... 기타 지역들
//		else {
//			throw new IllegalArgumentException("지원하지 않는 지역입니다.");
//		}
//	}  

}