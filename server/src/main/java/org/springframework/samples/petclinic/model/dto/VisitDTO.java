package org.springframework.samples.petclinic.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class VisitDTO {
    private Date date;
    private String petId;
    private String description;
}
