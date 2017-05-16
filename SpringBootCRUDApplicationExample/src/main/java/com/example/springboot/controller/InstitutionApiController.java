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

import com.example.springboot.model.Institution;
import com.example.springboot.service.InstitutionService;
import com.example.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class InstitutionApiController {

	public static final Logger logger = LoggerFactory.getLogger(InstitutionApiController.class);

	@Autowired
	InstitutionService institutionService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Institutions---------------------------------------------

	@RequestMapping(value = "/institution/", method = RequestMethod.GET)
	public ResponseEntity<List<Institution>> listAllInstitutions() {
		List<Institution> institutions = institutionService.findAllInstitutions();
		if (institutions.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Institution>>(institutions, HttpStatus.OK);
	}

	// -------------------Retrieve Single Institution------------------------------------------

	@RequestMapping(value = "/institution/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getInstitution(@PathVariable("id") long id) {
		logger.info("Fetching Institution with id {}", id);
		Institution institution = institutionService.findById(id);
		if (institution == null) {
			logger.error("Institution with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Institution with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Institution>(institution, HttpStatus.OK);
	}

	// -------------------Create a Institution-------------------------------------------

	@RequestMapping(value = "/institution/", method = RequestMethod.POST)
	public ResponseEntity<?> createInstitution(@RequestBody Institution institution, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Institution : {}", institution);

		if (institutionService.isInstitutionExist(institution)) {
			logger.error("Unable to create. A Institution with name {} already exist", institution.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Institution with name " + 
			institution.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		institutionService.saveInstitution(institution);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/institution/{id}").buildAndExpand(institution.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Institution ------------------------------------------------

	@RequestMapping(value = "/institution/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateInstitution(@PathVariable("id") long id, @RequestBody Institution institution) {
		logger.info("Updating Institution with id {}", id);

		Institution currentInstitution = institutionService.findById(id);

		if (currentInstitution == null) {
			logger.error("Unable to update. Institution with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Institution with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentInstitution.setName(institution.getName());
		currentInstitution.setDesc(institution.getDesc());

		institutionService.updateInstitution(currentInstitution);
		return new ResponseEntity<Institution>(currentInstitution, HttpStatus.OK);
	}

	// ------------------- Delete a Institution-----------------------------------------

	@RequestMapping(value = "/institution/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteInstitution(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Institution with id {}", id);

		Institution institution = institutionService.findById(id);
		if (institution == null) {
			logger.error("Unable to delete. Institution with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Institution with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		institutionService.deleteInstitutionById(id);
		return new ResponseEntity<Institution>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Institutions-----------------------------

	@RequestMapping(value = "/institution/", method = RequestMethod.DELETE)
	public ResponseEntity<Institution> deleteAllInstitutions() {
		logger.info("Deleting All Institutions");

		institutionService.deleteAllInstitutions();
		return new ResponseEntity<Institution>(HttpStatus.NO_CONTENT);
	}

}