package org.springframework.samples.petclinic.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PetDTO {

    private final String name;
    private final String ownerId;
    private final String typeId;
    private final Date birthDate;
}
