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

import org.springframework.samples.petclinic.dto.request.OwnerRequestDTO;
import org.springframework.samples.petclinic.dto.request.PetRequestDTO;
import org.springframework.samples.petclinic.dto.response.OwnerResponseDTO;
import org.springframework.samples.petclinic.dto.response.PetResponseDTO;
import org.springframework.samples.petclinic.dto.response.PetTypeResponseDTO;
import org.springframework.samples.petclinic.dto.response.SpecialtyResponseDTO;
import org.springframework.samples.petclinic.dto.response.VetResponseDTO;
import org.springframework.samples.petclinic.dto.response.VisitResponseDTO;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.dto.request.PetTypeRequestDTO;
import org.springframework.samples.petclinic.dto.request.SpecialtyRequestDTO;
import org.springframework.samples.petclinic.dto.request.VetRequestDTO;
import org.springframework.samples.petclinic.dto.request.VisitRequestDTO;


/**
 * Mostly used as a facade so all controllers have a single point of entry
 *
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 */
public interface ClinicService {

    PetResponseDTO findPetById(String id);
	Collection<PetResponseDTO> findAllPets();
    PetResponseDTO savePet(PetRequestDTO pet);
    PetResponseDTO updatePet(String id, PetRequestDTO pet);
	void deletePet(String id);

	Collection<VisitResponseDTO> findVisitsByPetId(String petId);
    VisitResponseDTO findVisitById(String visitId);
	Collection<VisitResponseDTO> findAllVisits();
    VisitResponseDTO saveVisit(VisitRequestDTO visit);
    VisitResponseDTO updateVisit(String id, VisitRequestDTO visit);
	void deleteVisit(String visitId);

	VetResponseDTO findVetById(String id);
	Collection<VetResponseDTO> findAllVets();
    VetResponseDTO saveVet(VetRequestDTO vet);
    VetResponseDTO updateVet(String id, VetRequestDTO vet);
	void deleteVet(String vetId);

	OwnerResponseDTO findOwnerById(String id);
	Collection<OwnerResponseDTO> findAllOwners();
    OwnerResponseDTO saveOwner(OwnerRequestDTO owner);
    OwnerResponseDTO updateOwner(String ownerId, OwnerRequestDTO owner);
	void deleteOwner(String ownerId);

	PetTypeResponseDTO findPetTypeById(String petTypeId);
	Collection<PetTypeResponseDTO> findAllPetTypes();
    PetTypeResponseDTO savePetType(PetTypeRequestDTO petType);
    PetTypeResponseDTO updatePetType(String id, PetTypeRequestDTO petType);
	void deletePetType(String petTypeId);

	SpecialtyResponseDTO findSpecialtyById(String specialtyId);
	Collection<SpecialtyResponseDTO> findAllSpecialties();
    SpecialtyResponseDTO saveSpecialty(SpecialtyRequestDTO specialty);
    SpecialtyResponseDTO updateSpecialty(String id, SpecialtyRequestDTO specialty);
	void deleteSpecialty(String specialtyID);
}
