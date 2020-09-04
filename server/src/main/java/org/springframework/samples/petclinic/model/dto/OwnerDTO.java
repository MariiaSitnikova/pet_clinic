package org.springframework.samples.petclinic.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OwnerDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String telephone;
    private String city;
}
