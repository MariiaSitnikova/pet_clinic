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
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.dto.response.OwnerResponseDTO;
import org.springframework.samples.petclinic.dto.response.PetResponseDTO;
import org.springframework.samples.petclinic.dto.response.PetTypeResponseDTO;
import org.springframework.samples.petclinic.dto.response.SpecialtyResponseDTO;
import org.springframework.samples.petclinic.dto.response.VetResponseDTO;
import org.springframework.samples.petclinic.dto.response.VisitResponseDTO;
import org.springframework.samples.petclinic.exception.EntityNotFoundException;
import org.springframework.samples.petclinic.dto.request.OwnerRequestDTO;
import org.springframework.samples.petclinic.dto.request.PetRequestDTO;
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
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.PetTypeRepository;
import org.springframework.samples.petclinic.repository.SpecialtyRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers
 * Also a placeholder for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 */
@Service

public class ClinicServiceImpl implements ClinicService {

    private final PetRepository petRepository;
    private final VetRepository vetRepository;
    private final OwnerRepository ownerRepository;
    private final VisitRepository visitRepository;
    private final SpecialtyRepository specialtyRepository;
	private final PetTypeRepository petTypeRepository;

    @Autowired
     public ClinicServiceImpl(
       		 PetRepository petRepository,
    		 VetRepository vetRepository,
    		 OwnerRepository ownerRepository,
    		 VisitRepository visitRepository,
    		 SpecialtyRepository specialtyRepository,
			 PetTypeRepository petTypeRepository) {
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.specialtyRepository = specialtyRepository;
		this.petTypeRepository = petTypeRepository;
    }

    @Override
    public PetResponseDTO findPetById(String id) {
        return new PetResponseDTO(findPet(id));
    }

    private Pet findPet(final String id) {
        return petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Pet"));
    }

    @Override
    public Collection<PetResponseDTO> findAllPets() {
        return petRepository.findAll().stream().map(PetResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public PetResponseDTO savePet(PetRequestDTO pet) {
        final Owner owner = findOwner(pet.getOwnerId());
        final PetType type = findPetType(pet.getTypeId());
        final Pet result = new Pet();
        result.setName(pet.getName());
        result.setBirthDate(pet.getBirthDate());
        result.setOwner(owner);
        result.setType(type);
        return new PetResponseDTO(petRepository.save(result));
    }

    @Override
    public PetResponseDTO updatePet(String id, PetRequestDTO pet) {
        final Pet existing = findPet(id);
        final Owner owner = findOwner(pet.getOwnerId());
        final PetType type = findPetType(pet.getTypeId());
        existing.setName(pet.getName());
        existing.setBirthDate(pet.getBirthDate());
        existing.setOwner(owner);
        existing.setType(type);
        return new PetResponseDTO(petRepository.save(existing));
    }

    @Override
    public void deletePet(String id) {
        findPetById(id);
        petTypeRepository.deleteById(id);
    }

    @Override
    public Collection<VisitResponseDTO> findVisitsByPetId(String petId) {
        return visitRepository.findByPetId(UUID.fromString(petId)).stream().map(VisitResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public VisitResponseDTO findVisitById(String visitId) {
        return new VisitResponseDTO(findVisit(visitId));
    }

    private Visit findVisit(String visitId) {
        return visitRepository.findById(visitId).orElseThrow(() -> new EntityNotFoundException(visitId, "Visit"));
    }

    @Override
    public Collection<VisitResponseDTO> findAllVisits() {
        return visitRepository.findAll().stream().map(VisitResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public VisitResponseDTO saveVisit(VisitRequestDTO visit) {
        final Pet pet = findPet(visit.getPetId());
        final Visit result = new Visit();
        result.setDate(visit.getDate());
        result.setDescription(visit.getDescription());
        result.setPet(pet);
        return new VisitResponseDTO(visitRepository.save(result));
    }

    @Override
    public VisitResponseDTO updateVisit(String id, VisitRequestDTO visit) {
        final Pet pet = findPet(visit.getPetId());
        final Visit result = findVisit(id);
        result.setId(UUID.fromString(id));
        result.setDate(visit.getDate());
        result.setDescription(visit.getDescription());
        result.setPet(pet);
        return new VisitResponseDTO(visitRepository.save(result));
    }

    @Override
    public void deleteVisit(String visitId) {
        findVetById(visitId);
        visitRepository.deleteById(visitId);
    }

    @Override
    public VetResponseDTO findVetById(String id) {
        return new VetResponseDTO(findVet(id));
    }

    private Vet findVet(String id) {
        return vetRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Vet"));
    }

    @Override
    public Collection<VetResponseDTO> findAllVets() {
        return vetRepository.findAll().stream().map(VetResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public VetResponseDTO saveVet(VetRequestDTO vet) {
        final Vet result = new Vet();
        vet.getSpecialitiesIds()
            .stream()
            .map(this::findSpecialty)
            .forEach(result::addSpecialty);
        result.setFirstName(vet.getFirstName());
        result.setLastName(vet.getLastName());
        return new VetResponseDTO(vetRepository.save(result));
    }

    @Override
    public VetResponseDTO updateVet(String id, VetRequestDTO vet) {
        final Vet result = findVet(id);
        result.setId(UUID.fromString(id));
        vet.getSpecialitiesIds()
            .stream()
            .map(this::findSpecialty)
            .forEach(result::addSpecialty);
        result.setFirstName(vet.getFirstName());
        result.setLastName(vet.getLastName());
        return new VetResponseDTO(vetRepository.save(result));
    }

    @Override
    public void deleteVet(String vetId) {
        findVetById(vetId);
        vetRepository.deleteById(vetId);
    }

    @Override
    public OwnerResponseDTO findOwnerById(String id) {
        return new OwnerResponseDTO(findOwner(id));
    }

    private Owner findOwner(String id) {
        return ownerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id, "Owner"));
    }

    @Override
    public Collection<OwnerResponseDTO> findAllOwners() {
        return ownerRepository.findAll().stream().map(OwnerResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public OwnerResponseDTO saveOwner(OwnerRequestDTO owner) {
        final Owner result = new Owner();
        result.setAddress(owner.getAddress());
        result.setCity(owner.getCity());
        result.setTelephone(owner.getTelephone());
        result.setFirstName(owner.getFirstName());
        result.setLastName(owner.getLastName());
        return new OwnerResponseDTO(ownerRepository.save(result));
    }

    @Override
    public OwnerResponseDTO updateOwner(String ownerId, OwnerRequestDTO owner) {
        final Owner result = findOwner(ownerId);
        result.setId(UUID.fromString(ownerId));
        result.setAddress(owner.getAddress());
        result.setCity(owner.getCity());
        result.setTelephone(owner.getTelephone());
        result.setFirstName(owner.getFirstName());
        result.setLastName(owner.getLastName());
        return new OwnerResponseDTO(ownerRepository.save(result));
    }

    @Override
    public void deleteOwner(String ownerId) {
        findOwnerById(ownerId);
        ownerRepository.deleteById(ownerId);
    }

    @Override
    public PetTypeResponseDTO findPetTypeById(String petTypeId) {
        return new PetTypeResponseDTO(findPetType(petTypeId));
    }

    private PetType findPetType(String petTypeId) {
        return petTypeRepository.findById(petTypeId)
            .orElseThrow(() -> new EntityNotFoundException(petTypeId, "PetType"));
    }

    @Override
    public Collection<PetTypeResponseDTO> findAllPetTypes() {
        return petTypeRepository.findAll().stream().map(PetTypeResponseDTO::new).collect(Collectors.toList());
    }


    @Override
    public PetTypeResponseDTO savePetType(PetTypeRequestDTO petType) {
        PetType result = new PetType();
        result.setName(petType.getName());
        return new PetTypeResponseDTO(petTypeRepository.save(result));
    }

    @Override
    public PetTypeResponseDTO updatePetType(String id, PetTypeRequestDTO petType) {
        PetType result = findPetType(id);
        result.setId(UUID.fromString(id));
        result.setName(petType.getName());
        return new PetTypeResponseDTO(petTypeRepository.save(result));
    }

    @Override
    public void deletePetType(String petTypeId) {
        findPetById(petTypeId);
        petTypeRepository.deleteById(petTypeId);
    }

    @Override
    public SpecialtyResponseDTO findSpecialtyById(String specialtyId) {
        return new SpecialtyResponseDTO(findSpecialty(specialtyId));
    }


    private Specialty findSpecialty(String specialtyId) {
        return specialtyRepository.findById(specialtyId).orElseThrow(() -> new EntityNotFoundException(specialtyId, "Speciality"));
    }

    @Override
    public Collection<SpecialtyResponseDTO> findAllSpecialties() {
        return specialtyRepository.findAll().stream().map(SpecialtyResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public SpecialtyResponseDTO saveSpecialty(SpecialtyRequestDTO specialty) {
        final Specialty result = new Specialty();
        result.setName(specialty.getName());
        return new SpecialtyResponseDTO(specialtyRepository.save(result));
    }

    @Override
    public SpecialtyResponseDTO updateSpecialty(String id, SpecialtyRequestDTO specialty) {
        final Specialty result = new Specialty();
        result.setId(UUID.fromString(id));
        result.setName(specialty.getName());
        return new SpecialtyResponseDTO(specialtyRepository.save(result));
    }

    @Override
    public void deleteSpecialty(String specialtyID) {
        findPetById(specialtyID);
        specialtyRepository.deleteById(specialtyID);
    }
}
