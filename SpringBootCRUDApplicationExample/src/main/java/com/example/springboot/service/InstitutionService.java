package com.example.springboot.service;


import java.util.List;

import com.example.springboot.model.Institution;

public interface InstitutionService {
	
	Institution findById(Long id);

	Institution findByName(String name);

	void saveInstitution(Institution institution);

	void updateInstitution(Institution institution);

	void deleteInstitutionById(Long id);

	void deleteAllInstitutions();

	List<Institution> findAllInstitutions();

	boolean isInstitutionExist(Institution institution);
}