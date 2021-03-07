package com.nikshepav.jwtauth.exception;

import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String entityName, String entityIdentifier) {
        super(entityName + " already exists: " + entityIdentifier);
    }
}
