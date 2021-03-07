package com.nikshepav.jwtauth.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, String entityIdentifier) {
        super(entityName + " does not exist: " + entityIdentifier);
    }
}
