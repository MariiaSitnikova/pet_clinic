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
import org.springframework.samples.petclinic.dto.request.PetRequestDTO;
import org.springframework.samples.petclinic.dto.response.PetResponseDTO;
import org.springframework.samples.petclinic.dto.response.PetTypeResponseDTO;
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
@RequestMapping("api/pets")
public class PetRestController {

	@Autowired
	private ClinicService clinicService;

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/{petId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<PetResponseDTO> getPet(@PathVariable("petId") String petId){
        PetResponseDTO pet = this.clinicService.findPetById(petId);
		return new ResponseEntity<>(pet, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<PetResponseDTO>> getPets(){
		Collection<PetResponseDTO> pets = this.clinicService.findAllPets();
		return new ResponseEntity<>(pets, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/pettypes", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<PetTypeResponseDTO>> getPetTypes(){
		return new ResponseEntity<>(this.clinicService.findAllPetTypes(), HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<PetResponseDTO> addPet(@RequestBody @Valid PetRequestDTO pet){
		final PetResponseDTO result = this.clinicService.savePet(pet);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/{petId}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<PetResponseDTO> updatePet(@PathVariable("petId") String petId, @RequestBody @Valid PetRequestDTO pet){
        PetResponseDTO result = this.clinicService.updatePet(petId, pet);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/{petId}", method = RequestMethod.DELETE, produces = "application/json")
	@Transactional
	public ResponseEntity<Void> deletePet(@PathVariable("petId") String petId){
		this.clinicService.deletePet(petId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}


}
