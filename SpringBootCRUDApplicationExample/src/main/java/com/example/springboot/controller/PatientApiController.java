package com.example.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.springboot.model.Patient;
import com.example.springboot.service.PatientService;
import com.example.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class PatientApiController {

	public static final Logger logger = LoggerFactory.getLogger(PatientApiController.class);

	@Autowired
	PatientService patientService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Patients---------------------------------------------

	@RequestMapping(value = "/patient/", method = RequestMethod.GET)
	public ResponseEntity<List<Patient>> listAllPatients() {
		logger.info("Fetching All Patients");
		List<Patient> patients = patientService.findAllPatients();
		if (patients.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
	}

	// -------------------Retrieve Single Patient------------------------------------------

	@RequestMapping(value = "/patient/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getPatient(@PathVariable("id") long id) {
		logger.info("Fetching Patient with id {}", id);
		Patient patient = patientService.findById(id);
		if (patient == null) {
			logger.error("Patient with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Patient with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Patient>(patient, HttpStatus.OK);
	}

	// -------------------Create a Patient-------------------------------------------

	@RequestMapping(value = "/patient/", method = RequestMethod.POST)
	public ResponseEntity<?> createPatient(@RequestBody Patient patient, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Patient : {}", patient);

		if (patientService.isPatientExist(patient)) {
			logger.error("Unable to create. A Patient with name {} already exist", patient.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Patient with name " + 
			patient.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		patientService.savePatient(patient);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/patient/{id}").buildAndExpand(patient.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Patient ------------------------------------------------

	@RequestMapping(value = "/patient/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePatient(@PathVariable("id") long id, @RequestBody Patient patient) {
		logger.info("Updating Patient with id {}", id);

		Patient currentPatient = patientService.findById(id);

		if (currentPatient == null) {
			logger.error("Unable to update. Patient with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Patient with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentPatient.setName(patient.getName());
		currentPatient.setDob(patient.getDob());
		currentPatient.setGender(patient.getGender());

		patientService.updatePatient(currentPatient);
		return new ResponseEntity<Patient>(currentPatient, HttpStatus.OK);
	}

	// ------------------- Delete a Patient-----------------------------------------

	@RequestMapping(value = "/patient/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePatient(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Patient with id {}", id);

		Patient patient = patientService.findById(id);
		if (patient == null) {
			logger.error("Unable to delete. Patient with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Patient with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		patientService.deletePatientById(id);
		return new ResponseEntity<Patient>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Patients-----------------------------

	@RequestMapping(value = "/patient/", method = RequestMethod.DELETE)
	public ResponseEntity<Patient> deleteAllPatients() {
		logger.info("Deleting All Patients");

		patientService.deleteAllPatients();
		return new ResponseEntity<Patient>(HttpStatus.NO_CONTENT);
	}

}