/*
 * Copyright 2016-2018 the original author or authors.
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
import org.springframework.samples.petclinic.dto.response.VetResponseDTO;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.dto.request.VetRequestDTO;
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
@RequestMapping("api/vets")
public class VetRestController {

	@Autowired
	private ClinicService clinicService;

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<VetResponseDTO>> getAllVets(){
		Collection<VetResponseDTO> vets = this.clinicService.findAllVets();
		return new ResponseEntity<>(vets, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{vetId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<VetResponseDTO> getVet(@PathVariable("vetId") String vetId){
        VetResponseDTO vet = this.clinicService.findVetById(vetId);
		return new ResponseEntity<>(vet, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<VetResponseDTO> addVet(@RequestBody @Valid VetRequestDTO vet){
		final VetResponseDTO result = this.clinicService.saveVet(vet);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{vetId}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<VetResponseDTO> updateVet(@PathVariable("vetId") String vetId, @RequestBody @Valid VetRequestDTO vet){
        VetResponseDTO result = this.clinicService.updateVet(vetId, vet);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{vetId}", method = RequestMethod.DELETE, produces = "application/json")
	@Transactional
	public ResponseEntity<Void> deleteVet(@PathVariable("vetId") String vetId){
		this.clinicService.deleteVet(vetId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}



}
