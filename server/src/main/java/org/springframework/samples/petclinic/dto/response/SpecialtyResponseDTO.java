package org.springframework.samples.petclinic.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.samples.petclinic.model.Specialty;

@Data
@NoArgsConstructor
public class SpecialtyResponseDTO {
    private String id;
    private String name;

    public SpecialtyResponseDTO(final Specialty specialty) {
        this.id = specialty.getId();
        this.name = specialty.getName();
    }
}
