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

import com.example.springboot.model.Examination;
import com.example.springboot.service.ExaminationService;
import com.example.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class ExaminationApiController {

	public static final Logger logger = LoggerFactory.getLogger(ExaminationApiController.class);

	@Autowired
	ExaminationService examinationService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Examinations---------------------------------------------

	@RequestMapping(value = "/examination/", method = RequestMethod.GET)
	public ResponseEntity<List<Examination>> listAllExaminations() {
		List<Examination> examinations = examinationService.findAllExaminations();
		if (examinations.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Examination>>(examinations, HttpStatus.OK);
	}

	// -------------------Retrieve Single Examination------------------------------------------

	@RequestMapping(value = "/examination/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getExamination(@PathVariable("id") long id) {
		logger.info("Fetching Examination with id {}", id);
		Examination examination = examinationService.findById(id);
		if (examination == null) {
			logger.error("Examination with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Examination with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Examination>(examination, HttpStatus.OK);
	}

	// -------------------Create a Examination-------------------------------------------

	@RequestMapping(value = "/examination/", method = RequestMethod.POST)
	public ResponseEntity<?> createExamination(@RequestBody Examination examination, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Examination : {}", examination);

		if (examinationService.isExaminationExist(examination)) {
			logger.error("Unable to create. A Examination with name {} already exist", examination.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Examination with name " + 
			examination.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		examinationService.saveExamination(examination);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/examination/{id}").buildAndExpand(examination.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Examination ------------------------------------------------

	@RequestMapping(value = "/examination/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateExamination(@PathVariable("id") long id, @RequestBody Examination examination) {
		logger.info("Updating Examination with id {}", id);

		Examination currentExamination = examinationService.findById(id);

		if (currentExamination == null) {
			logger.error("Unable to update. Examination with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Examination with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentExamination.setName(examination.getName());
		currentExamination.setDesc(examination.getDesc());
		currentExamination.setExamDate(examination.getExamDate());
		currentExamination.setWeight(examination.getWeight());
		currentExamination.setHeight(examination.getHeight());

		examinationService.updateExamination(currentExamination);
		return new ResponseEntity<Examination>(currentExamination, HttpStatus.OK);
	}

	// ------------------- Delete a Examination-----------------------------------------

	@RequestMapping(value = "/examination/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteExamination(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Examination with id {}", id);

		Examination examination = examinationService.findById(id);
		if (examination == null) {
			logger.error("Unable to delete. Examination with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Examination with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		examinationService.deleteExaminationById(id);
		return new ResponseEntity<Examination>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Examinations-----------------------------

	@RequestMapping(value = "/examination/", method = RequestMethod.DELETE)
	public ResponseEntity<Examination> deleteAllExaminations() {
		logger.info("Deleting All Examinations");

		examinationService.deleteAllExaminations();
		return new ResponseEntity<Examination>(HttpStatus.NO_CONTENT);
	}

}