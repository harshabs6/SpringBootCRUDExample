package com.example.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.model.Examination;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {

	Examination findByName(String name);

}
