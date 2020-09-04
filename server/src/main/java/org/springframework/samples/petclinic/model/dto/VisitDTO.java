package org.springframework.samples.petclinic.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class VisitDTO {

    private final Date date;
    private final String petId;
    private final String description;
}
