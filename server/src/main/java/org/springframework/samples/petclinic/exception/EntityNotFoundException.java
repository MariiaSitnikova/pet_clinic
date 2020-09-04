package org.springframework.samples.petclinic.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String id, String entity) {
        super( entity + " with id: " + id + " not found");
    }
}
