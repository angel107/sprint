package com.example.demo.weatherApi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class WeatherDTO {

	int forecastTime;
	int temperature;
	String weather;
	int rainPossi;
	LocalDateTime regDate; // 등록일자

	LocalDateTime modDate; // 수정일자

}
