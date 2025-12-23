package com.transport.repositories.Client;

import com.transport.entities.Client;
import com.transport.repositories.IRepository;

import java.util.List;
import java.util.Optional;

public interface IClientRepository extends IRepository<Client, Long> {
    /**
     * Find client by ID with company data (JOIN FETCH to avoid N+1)
     */
    Optional<Client> findByIdWithCompany(Long id);

    /**
     * Check if client exists by name within the same company
     */
    boolean existsByNameAndCompany(String name, Long companyId);

    /**
     * Find all clients with company data (JOIN FETCH to avoid N+1)
     */
    List<Client> findAllWithCompany();
}
