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

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.dto.response.VisitResponseDTO;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.dto.request.VisitRequestDTO;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vitaliy Fedoriv
 *
 */

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/visits")
public class VisitRestController {

	@Autowired
	private ClinicService clinicService;

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<VisitResponseDTO>> getAllVisits(){
		Collection<VisitResponseDTO> visits = this.clinicService.findAllVisits();
		return new ResponseEntity<>(visits, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/{visitId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<VisitResponseDTO> getVisit(@PathVariable("visitId") String visitId){
        VisitResponseDTO visit = this.clinicService.findVisitById(visitId);
		return new ResponseEntity<>(visit, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<VisitResponseDTO> addVisit(@RequestBody @Valid VisitRequestDTO visit) {
        VisitResponseDTO result = this.clinicService.saveVisit(visit);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/{visitId}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<VisitResponseDTO> updateVisit(@PathVariable("visitId") String visitId, @RequestBody @Valid VisitRequestDTO visit){
        VisitResponseDTO result = this.clinicService.updateVisit(visitId, visit);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/{visitId}", method = RequestMethod.DELETE, produces = "application/json")
	@Transactional
	public ResponseEntity<Void> deleteVisit(@PathVariable("visitId") String visitId){
		this.clinicService.deleteVisit(visitId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
