package org.springframework.samples.petclinic.model.dto;

import lombok.Data;

@Data
public class OwnerDTO {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String telephone;
    private final String city;
}
