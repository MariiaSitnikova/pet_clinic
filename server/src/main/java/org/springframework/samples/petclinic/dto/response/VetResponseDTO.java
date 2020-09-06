package org.springframework.samples.petclinic.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.samples.petclinic.model.Vet;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class VetResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private Set<SpecialtyResponseDTO> specialities;

    public VetResponseDTO(final Vet vet) {
        this.id = vet.getId();
        this.firstName = vet.getFirstName();
        this.lastName = vet.getLastName();
        this.specialities = vet.getSpecialties().stream().map(SpecialtyResponseDTO::new).collect(Collectors.toSet());
    }
}
