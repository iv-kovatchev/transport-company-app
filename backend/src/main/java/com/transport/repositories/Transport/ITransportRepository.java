package com.transport.repositories.Transport;

import com.transport.entities.Transport;
import com.transport.repositories.IRepository;

import java.util.List;
import java.util.Optional;

public interface ITransportRepository extends IRepository<Transport, Long> {
    /**
     * Find transport by ID with ALL relationships (company, client, vehicle, driver)
     */
    Optional<Transport> findByIdWithRelationships(Long id);

    /**
     * Find all transports with ALL relationships
     */
    List<Transport> findAllWithRelationships();

    /**
     * Check if vehicle has existing transports (for DELETE restriction)
     */
    boolean existsByVehicleId(Long vehicleId);

    /**
     * Check if employee (driver) has existing transports (for DELETE restriction)
     */
    boolean existsByDriverId(Long driverId);

    /**
     * Check if client has existing transports (for DELETE restriction)
     */
    boolean existsByClientId(Long clientId);

    /**
     * Find all transports filtered by payment status
     */
    List<Transport> findAllByPaymentStatus(Boolean isPaid);

    /**
     * Find all transports by company filtered by payment status
     */
    List<Transport> findAllByCompanyIdAndPaymentStatus(Long companyId, Boolean isPaid);
}
