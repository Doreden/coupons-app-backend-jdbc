package com.dor.couponssystem.db.dao;

import com.dor.couponssystem.exceptions.EntityCrudException;

public abstract class UserDAO <ID,Entity> implements CrudDAO<ID,Entity> {

    public abstract Entity readByEmail(final String email) throws EntityCrudException;


    //public abstract boolean isExists(final String email) throws EntityCrudException;

    public boolean isExists(String email) throws EntityCrudException {
    return readByEmail(email) != null;
    }
}
