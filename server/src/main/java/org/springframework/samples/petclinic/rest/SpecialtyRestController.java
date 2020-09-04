/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.dto.SpecialtyDTO;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;

/**
 * @author Vitaliy Fedoriv
 *
 */

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/specialties")
public class SpecialtyRestController {

	@Autowired
	private ClinicService clinicService;

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<Specialty>> getAllSpecialtys(){
		final Collection<Specialty> result = this.clinicService.findAllSpecialties();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{specialtyId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Specialty> getSpecialty(@PathVariable("specialtyId") String specialtyId){
		Specialty specialty = this.clinicService.findSpecialtyById(specialtyId);
		return new ResponseEntity<>(specialty, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Specialty> addSpecialty(@RequestBody @Valid SpecialtyDTO specialty){
		final Specialty result = this.clinicService.saveSpecialty(specialty);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{specialtyId}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Specialty> updateSpecialty(@PathVariable("specialtyId") String specialtyId, @RequestBody @Valid SpecialtyDTO specialty){
		Specialty result = this.clinicService.updateSpecialty(specialtyId, specialty);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{specialtyId}", method = RequestMethod.DELETE, produces = "application/json")
	@Transactional
	public ResponseEntity<Void> deleteSpecialty(@PathVariable("specialtyId") String specialtyId){
		this.clinicService.deleteSpecialty(specialtyId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
