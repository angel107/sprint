package com.example.demo.marathonBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.marathonBoard.entity.Marathon;

public interface MarathonRepository extends JpaRepository<Marathon, Integer> {

}
