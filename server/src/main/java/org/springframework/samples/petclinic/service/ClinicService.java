/*
 * Copyright 2002-2017 the original author or authors.
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
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.samples.petclinic.model.dto.OwnerDTO;
import org.springframework.samples.petclinic.model.dto.PetDTO;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.dto.PetTypeDTO;
import org.springframework.samples.petclinic.model.dto.SpecialtyDTO;
import org.springframework.samples.petclinic.model.dto.VetDTO;
import org.springframework.samples.petclinic.model.dto.VisitDTO;


/**
 * Mostly used as a facade so all controllers have a single point of entry
 *
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 */
public interface ClinicService {

	Pet findPetById(String id);
	Collection<Pet> findAllPets();
	Pet savePet(PetDTO pet);
	Pet updatePet(String id, PetDTO pet);
	void deletePet(String id);

	Collection<Visit> findVisitsByPetId(String petId);
	Visit findVisitById(String visitId);
	Collection<Visit> findAllVisits();
	Visit saveVisit(VisitDTO visit);
    Visit updateVisit(String id, VisitDTO visit);
	void deleteVisit(String visitId);

	Vet findVetById(String id);
	Collection<Vet> findAllVets();
	Vet saveVet(VetDTO vet);
	Vet updateVet(String id, VetDTO vet);
	void deleteVet(String vetId);

	Owner findOwnerById(String id);
	Collection<Owner> findAllOwners();
	Owner saveOwner(OwnerDTO owner);
    Owner updateOwner(String ownerId, OwnerDTO owner);
	void deleteOwner(String ownerId);

	PetType findPetTypeById(String petTypeId);
	Collection<PetType> findAllPetTypes();
	PetType savePetType(PetTypeDTO petType);
	PetType updatePetType(String id, PetTypeDTO petType);
	void deletePetType(String petTypeId);

	Specialty findSpecialtyById(String specialtyId);
	Collection<Specialty> findAllSpecialties();
	Specialty saveSpecialty(SpecialtyDTO specialty);
    Specialty updateSpecialty(String id, SpecialtyDTO specialty);
	void deleteSpecialty(String specialtyID);
}
