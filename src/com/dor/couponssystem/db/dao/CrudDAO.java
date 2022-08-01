package com.dor.couponssystem.db.dao;

import com.dor.couponssystem.exceptions.EntityCrudException;

import java.util.List;

public interface CrudDAO<ID,Entity>{
    ID create(final Entity entity) throws EntityCrudException;
    Entity read(final ID id) throws EntityCrudException;
    void update(final Entity entity) throws EntityCrudException;
    void delete(final ID id) throws EntityCrudException;
    List<Entity> readAll() throws EntityCrudException;
}
