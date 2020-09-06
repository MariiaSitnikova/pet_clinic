package org.springframework.samples.petclinic.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.samples.petclinic.model.PetType;

@Data
@NoArgsConstructor
public class PetTypeResponseDTO {
   private String id;
   private String name;

    public PetTypeResponseDTO(final PetType type) {
        this.id = type.getId();
        this.name = type.getName();
    }
}
