package org.springframework.samples.petclinic.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PetRequestDTO {
    private String name;
    private String ownerId;
    private String typeId;
    private Date birthDate;
}
