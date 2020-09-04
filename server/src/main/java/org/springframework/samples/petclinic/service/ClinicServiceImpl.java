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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.exception.EntityNotFoundException;
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

    private PetRepository petRepository;
    private VetRepository vetRepository;
    private OwnerRepository ownerRepository;
    private VisitRepository visitRepository;
    private SpecialtyRepository specialtyRepository;
	private PetTypeRepository petTypeRepository;

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
    @Transactional
    public Pet findPetById(String id) {
        return petRepository.findById(UUID.fromString(id)).orElseThrow(() -> new EntityNotFoundException(id, "Pet"));
    }

    @Override
    public Collection<Pet> findAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet savePet(PetDTO pet) {
        final Owner owner = findOwnerById(pet.getOwnerId());
        final PetType type = findPetTypeById(pet.getTypeId());
        final Pet result = new Pet();
        result.setName(pet.getName());
        result.setBirthDate(pet.getBirthDate());
        result.setOwner(owner);
        result.setType(type);
        return petRepository.save(result);
    }

    @Override
    public Pet updatePet(String id, PetDTO pet) {
        final Pet existing = findPetById(id);
        final Owner owner = findOwnerById(pet.getOwnerId());
        final PetType type = findPetTypeById(pet.getTypeId());
        existing.setName(pet.getName());
        existing.setBirthDate(pet.getBirthDate());
        existing.setOwner(owner);
        existing.setType(type);
        return petRepository.save(existing);
    }

    @Override
    public void deletePet(String id) {
        findPetById(id);
        petTypeRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public Collection<Visit> findVisitsByPetId(String petId) {
        return visitRepository.findByPetId(UUID.fromString(petId));
    }

    @Override
    public Visit findVisitById(String visitId) {
        return visitRepository.findById(UUID.fromString(visitId)).orElseThrow(() -> new EntityNotFoundException(visitId, "Visit"));
    }

    @Override
    public Collection<Visit> findAllVisits() {
        return visitRepository.findAll();
    }

    @Override
    public Visit saveVisit(VisitDTO visit) {
        final Pet pet = findPetById(visit.getPetId());
        final Visit result = new Visit();
        result.setDate(visit.getDate());
        result.setDescription(visit.getDescription());
        result.setPet(pet);
        return visitRepository.save(result);
    }

    @Override
    public Visit updateVisit(String id, VisitDTO visit) {
        final Pet pet = findPetById(visit.getPetId());
        final Visit result = new Visit();
        result.setId(UUID.fromString(id));
        result.setDate(visit.getDate());
        result.setDescription(visit.getDescription());
        result.setPet(pet);
        return visitRepository.save(result);
    }

    @Override
    public void deleteVisit(String visitId) {
        findVetById(visitId);
        visitRepository.deleteById(UUID.fromString(visitId));
    }

    @Override
    public Vet findVetById(String id) {
        return vetRepository.findById(UUID.fromString(id)).orElseThrow(() -> new EntityNotFoundException(id, "Vet"));
    }

    @Override
    public Collection<Vet> findAllVets() {
        return vetRepository.findAll();
    }

    @Override
    public Vet saveVet(VetDTO vet) {
        final Vet result = new Vet();
        vet.getSpecialitiesIds()
            .stream()
            .map(this::findSpecialtyById)
            .forEach(result::addSpecialty);
        result.setFirstName(vet.getFirstName());
        result.setLastName(vet.getLastName());
        return vetRepository.save(result);
    }

    @Override
    public Vet updateVet(String id, VetDTO vet) {
        final Vet result = new Vet();
        result.setId(UUID.fromString(id));
        vet.getSpecialitiesIds()
            .stream()
            .map(this::findSpecialtyById)
            .forEach(result::addSpecialty);
        result.setFirstName(vet.getFirstName());
        result.setLastName(vet.getLastName());
        return vetRepository.save(result);
    }

    @Override
    public void deleteVet(String vetId) {
        findVetById(vetId);
        vetRepository.deleteById(UUID.fromString(vetId));
    }

    @Override
    public Owner findOwnerById(String id) {
        return ownerRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(id, "Owner"));
    }

    @Override
    public Collection<Owner> findAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public Owner saveOwner(OwnerDTO owner) {
        final Owner result = new Owner();
        result.setAddress(owner.getAddress());
        result.setCity(owner.getCity());
        result.setTelephone(owner.getTelephone());
        result.setFirstName(owner.getFirstName());
        result.setLastName(owner.getLastName());
        return ownerRepository.save(result);
    }

    @Override
    public Owner updateOwner(String ownerId, OwnerDTO owner) {
        final Owner result = new Owner();
        result.setId(UUID.fromString(ownerId));
        result.setAddress(owner.getAddress());
        result.setCity(owner.getCity());
        result.setTelephone(owner.getTelephone());
        result.setFirstName(owner.getFirstName());
        result.setLastName(owner.getLastName());
        return ownerRepository.save(result);
    }

    @Override
    public void deleteOwner(String ownerId) {
        findOwnerById(ownerId);
        ownerRepository.deleteById(UUID.fromString(ownerId));
    }

    @Override
    public PetType findPetTypeById(String petTypeId) {
        return petTypeRepository.findById(UUID.fromString(petTypeId))
            .orElseThrow(() -> new EntityNotFoundException(petTypeId, "PetType"));
    }

    @Override
    public Collection<PetType> findAllPetTypes() {
        return petTypeRepository.findAll();
    }


    @Override
    public PetType savePetType(PetTypeDTO petType) {
        PetType result = new PetType();
        result.setName(petType.getName());
        return petTypeRepository.save(result);
    }

    @Override
    public PetType updatePetType(String id, PetTypeDTO petType) {
        PetType result = new PetType();
        result.setId(UUID.fromString(id));
        result.setName(petType.getName());
        return petTypeRepository.save(result);
    }

    @Override
    public void deletePetType(String petTypeId) {
        findPetById(petTypeId);
        petTypeRepository.deleteById(UUID.fromString(petTypeId));
    }

    @Override
    public Specialty findSpecialtyById(String specialtyId) {
        return specialtyRepository.findById(UUID.fromString(specialtyId)).orElseThrow(() -> new EntityNotFoundException(specialtyId, "Speciality"));
    }

    @Override
    public Collection<Specialty> findAllSpecialties() {
        return specialtyRepository.findAll();
    }

    @Override
    public Specialty saveSpecialty(SpecialtyDTO specialty) {
        final Specialty result = new Specialty();
        result.setName(specialty.getName());
        return specialtyRepository.save(result);
    }

    @Override
    public Specialty updateSpecialty(String id, SpecialtyDTO specialty) {
        final Specialty result = new Specialty();
        result.setId(UUID.fromString(id));
        result.setName(specialty.getName());
        return specialtyRepository.save(result);
    }

    @Override
    public void deleteSpecialty(String specialtyID) {
        findPetById(specialtyID);
        specialtyRepository.deleteById(UUID.fromString(specialtyID));
    }
}
