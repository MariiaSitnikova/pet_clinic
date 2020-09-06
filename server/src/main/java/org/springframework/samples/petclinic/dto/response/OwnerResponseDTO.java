package org.springframework.samples.petclinic.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.samples.petclinic.model.Owner;

@Data
@NoArgsConstructor
public class OwnerResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String telephone;
    private String city;

    public OwnerResponseDTO(final Owner owner) {
        this.id = owner.getId();
        this.firstName = owner.getFirstName();
        this.lastName = owner.getLastName();
        this.address = owner.getAddress();
        this.city = owner.getCity();
        this.telephone = owner.getTelephone();
    }
}
