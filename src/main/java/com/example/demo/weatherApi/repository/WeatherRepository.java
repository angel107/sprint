package com.example.demo.weatherApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.weatherApi.entity.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {

}
