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
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.dto.PetTypeDTO;
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

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/pettypes")
public class PetTypeRestController {

	@Autowired
	private ClinicService clinicService;

    @PreAuthorize( "hasAnyRole(@roles.OWNER_ADMIN, @roles.VET_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<PetType>> getAllPetTypes(){
        Collection<PetType> result = this.clinicService.findAllPetTypes();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

    @PreAuthorize( "hasAnyRole(@roles.OWNER_ADMIN, @roles.VET_ADMIN)" )
	@RequestMapping(value = "/{petTypeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<PetType> getPetType(@PathVariable("petTypeId") String petTypeId){
		PetType petType = this.clinicService.findPetTypeById(petTypeId);
		return new ResponseEntity<>(petType, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<PetType> addPetType(@RequestBody @Valid PetTypeDTO petType){
		PetType type = this.clinicService.savePetType(petType);
		return new ResponseEntity<>(type, HttpStatus.CREATED);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{petTypeId}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<PetType> updatePetType(@PathVariable("petTypeId") String petTypeId, @RequestBody @Valid PetTypeDTO petType){
        PetType result = this.clinicService.updatePetType(petTypeId, petType);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{petTypeId}", method = RequestMethod.DELETE, produces = "application/json")
	@Transactional
	public ResponseEntity<Void> deletePetType(@PathVariable("petTypeId") String petTypeId){
		this.clinicService.deletePetType(petTypeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}