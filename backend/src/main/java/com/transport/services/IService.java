package com.transport.services;

import java.util.List;

/**
 * Generic service interface for CRUD operations4
*/
public interface IService<CreateRequest, UpdateRequest, Response, ID> {
    /**
     * Create a new entity
     */
    Response create(CreateRequest request);

    /**
     * Get entity by ID
     */
    Response getById(ID id);

    /**
     * Get all entities
     */
    List<Response> getAll();

    /**
     * Update existing entity
     */
    Response update(ID id, UpdateRequest request);

    /**
     * Delete entity by ID
     */
    void delete(ID id);
}