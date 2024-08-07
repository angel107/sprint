package com.example.demo.weatherApi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.weatherApi.dto.WeatherDTO;
import com.example.demo.weatherApi.entity.Weather;
import com.example.demo.weatherApi.repository.WeatherRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {

	@Autowired
	WeatherRepository repository;

	String serviceKey = "SUGpdq3ufUPY4EA%2Fy6EDsonRTsw4bY%2F9ZInDHY304YJ1hxQYOh1TfWoWy4kVz1L%2Fm9P7ImHvkKGyD83FxWpSjQ%3D%3D";
	String dataType = "JSON";
	String code = "11B20201";   // 기상청 인천 지역 코드

	public String getWeather() throws IOException {
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1360000/VilageFcstMsgService/getLandFcst"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /* Service Key */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("20", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "="
				+ URLEncoder.encode(dataType, "UTF-8")); /* 요청자료형식(XML/JSON) Default: XML */
		urlBuilder.append("&" + URLEncoder.encode("regId", "UTF-8") + "=" + URLEncoder.encode(code,
				"UTF-8")); /* 11A00101(백령도), 11B10101 (서울), 11B20201(인천) 등... 별첨 엑셀자료 참조(‘육상’ 구분 값 참고) */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();

		return sb.toString();
	}

	public List<Weather> jsonToEntity() throws IOException {

		// 매퍼 클래스 생성
		ObjectMapper mapper = new ObjectMapper();

		// 분석할 수 없는 구문을 무시하는 옵션 설정
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// 날씨 데이터 가져오기
		String weather = getWeather();

		Root root = null;

		// JSON 문자열을 클래스로 변환
		root = mapper.readValue(weather, Root.class);

		List<Weather> list = new ArrayList<>();
		// Root 객체의 필드 값을 사용하여 Weather 엔티티 생성
		for (Item item : root.getResponse().getBody().getItems().getItem()) {
			Weather entity = Weather.builder().forecastTime(item.numEf).temperature(item.getTa()).weather(item.getWf())
					.rainPossi(item.getRnSt()).build();
			list.add(entity);
			repository.save(entity);
		}

		return list; // entity 리스트
	}

	public List<WeatherDTO> entityToDto() throws IOException {

		List<Weather> weatherList = jsonToEntity();

		List<WeatherDTO> list = new ArrayList<>();

		for (Weather entity : weatherList) {

			WeatherDTO dto = WeatherDTO.builder().forecastTime(entity.getForecastTime()).temperature(entity.getTemperature())
					.weather(entity.getWeather()).rainPossi(entity.getRainPossi()).build();

			list.add(dto);
		}
		return list; // dto 리스트

	}
}