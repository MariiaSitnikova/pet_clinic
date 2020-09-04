package org.springframework.samples.petclinic.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class VetDTO {
    private String firstName;
    private String lastName;
    private Set<String> specialitiesIds;
}
