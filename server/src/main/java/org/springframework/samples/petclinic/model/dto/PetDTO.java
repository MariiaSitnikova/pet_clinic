package org.springframework.samples.petclinic.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PetDTO {
    private String name;
    private String ownerId;
    private String typeId;
    private Date birthDate;
}
