package com.transport.repositories.Vehicle;

import com.transport.entities.Vehicle;
import com.transport.repositories.IRepository;

import java.util.List;
import java.util.Optional;

public interface IVehicleRepository extends IRepository<Vehicle, Long> {
    /**
     * Find vehicle by ID with company data (JOIN FETCH to avoid N+1)
     */
    Optional<Vehicle> findByIdWithCompany(Long id);

    /**
     * Find all vehicles with company data (JOIN FETCH to avoid N+1)
     */
    List<Vehicle> findAllWithCompany();

    /**
     * Check if license plate exists for a different vehicle (for update validation)
     */
    boolean existsByLicensePlateAndIdNot(String licensePlate, Long id);

    List<Vehicle> findByCompanyId(Long companyId);
}
