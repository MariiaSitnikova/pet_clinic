package org.springframework.samples.petclinic.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.samples.petclinic.model.Pet;

import java.util.Date;

@Data
@NoArgsConstructor
public class PetResponseDTO {
    private String id;
    private String name;
    private OwnerResponseDTO owner;
    private PetTypeResponseDTO type;
    private Date birthDate;

    public PetResponseDTO(final Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.birthDate = pet.getBirthDate();
        this.owner = new OwnerResponseDTO(pet.getOwner());
        this.type = new PetTypeResponseDTO(pet.getType());
    }
}
