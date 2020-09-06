package org.springframework.samples.petclinic.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OwnerRequestDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String telephone;
    private String city;
}
