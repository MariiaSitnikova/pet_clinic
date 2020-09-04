package org.springframework.samples.petclinic.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class VetDTO {

    private final String firstName;
    private final String lastName;
    private final Set<String> specialitiesIds;
}
