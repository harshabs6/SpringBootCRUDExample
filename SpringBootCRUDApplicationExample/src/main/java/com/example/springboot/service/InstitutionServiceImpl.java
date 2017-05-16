package com.example.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.model.Institution;
import com.example.springboot.repositories.InstitutionRepository;



@Service("institutionService")
@Transactional
public class InstitutionServiceImpl implements InstitutionService{

	@Autowired
	private InstitutionRepository institutionRepository;

	public Institution  findById(Long id) {
		return institutionRepository.findOne(id);
	}

	public Institution findByName(String name) {
		return institutionRepository.findByName(name);
	}

	public void saveInstitution(Institution user) {
		institutionRepository.save(user);
	}

	public void updateInstitution(Institution user){
		saveInstitution(user);
	}

	public void deleteUserById(Long id){
		institutionRepository.delete(id);
	}

	public void deleteAllInstitutions(){
		institutionRepository.deleteAll();
	}

	public List<Institution> findAllInstitutions(){
		return institutionRepository.findAll();
	}

	public boolean isUserExist(Institution user) {
		return findByName(user.getName()) != null;
	}


	@Override
	public void deleteInstitutionById(Long id) {
		institutionRepository.delete(id);
		
	}

	@Override
	public boolean isInstitutionExist(Institution institution) {
		// TODO Auto-generated method stub
		return findByName(institution.getName()) != null;
	}

	

}
