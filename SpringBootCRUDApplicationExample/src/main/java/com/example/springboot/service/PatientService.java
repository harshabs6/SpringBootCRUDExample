package com.example.springboot.service;


import java.util.Date;
import java.util.List;

import com.example.springboot.model.Patient;

public interface PatientService {
	
	Patient findById(Long id);

	Patient findByName(String name);

	void savePatient(Patient patient);

	void updatePatient(Patient patient);

	void deletePatientById(Long id);

	void deleteAllPatients();

	List<Patient> findAllPatients();

	boolean isPatientExist(Patient patient);
	
	Integer findByDOB(Date dob);
}