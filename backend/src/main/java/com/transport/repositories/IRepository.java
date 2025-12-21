package com.transport.repositories;

import java.util.List;
import java.util.Optional;

public interface IRepository<T, ID> {
    //Save a new entity to the database
    T save(T entity);

    //Find entity by ID
    Optional<T> findById(ID id);

    //Get all entities
    List<T> findAll();

    //Update existing entity
    T update(T entity);

    //Delete entity by ID
    void delete(ID id);
}
