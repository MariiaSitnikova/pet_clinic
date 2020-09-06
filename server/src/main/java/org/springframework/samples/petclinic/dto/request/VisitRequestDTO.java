package org.springframework.samples.petclinic.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class VisitRequestDTO {
    private Date date;
    private String petId;
    private String description;
}
