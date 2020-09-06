package org.springframework.samples.petclinic.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.samples.petclinic.model.Visit;

import java.util.Date;

@Data
@NoArgsConstructor
public class VisitResponseDTO {
    private String id;
    private Date date;
    private PetResponseDTO pet;
    private String description;

    public VisitResponseDTO(final Visit visit) {
        this.id = visit.getId();
        this.date = visit.getDate();
        this.description = visit.getDescription();
        this.pet = new PetResponseDTO(visit.getPet());
    }
}
