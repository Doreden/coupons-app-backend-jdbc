package com.dor.couponssystem.exceptions;

import com.dor.couponssystem.enums.CrudOperation;
import com.dor.couponssystem.enums.EntityType;

public class EntityCrudException extends Exception {

    public EntityCrudException(final EntityType  entityType, CrudOperation crudOperation, Exception e) {
        super(String.format("failed to %s %s entity", crudOperation.name(), entityType.name()),e);
    }
}
