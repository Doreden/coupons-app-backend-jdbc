package com.dor.couponssystem.exceptions;

import com.dor.couponssystem.enums.EntityType;

public class EmailAlreadyExistsException extends Exception {

    public EmailAlreadyExistsException(String email, EntityType entityType) {
        super(String.format(" The %s email %s is already exists in the db", email, entityType.name()));
    }
}
