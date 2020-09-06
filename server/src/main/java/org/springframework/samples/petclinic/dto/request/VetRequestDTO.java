package org.springframework.samples.petclinic.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class VetRequestDTO {
    private String firstName;
    private String lastName;
    private Set<String> specialitiesIds;
}
