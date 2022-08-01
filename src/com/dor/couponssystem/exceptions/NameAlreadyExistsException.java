package com.dor.couponssystem.exceptions;

import com.dor.couponssystem.enums.EntityType;

public class NameAlreadyExistsException extends Exception{

    public NameAlreadyExistsException(String name, EntityType entityType) {
        super(String.format(" The %s name %s is already exists in the db",name,entityType.name()));
    }
}
