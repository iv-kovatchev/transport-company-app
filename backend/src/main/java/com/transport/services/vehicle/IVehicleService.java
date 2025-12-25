package com.transport.services.vehicle;

import com.transport.dtos.vehicle.VehicleCreateRequest;
import com.transport.dtos.vehicle.VehicleResponse;
import com.transport.dtos.vehicle.VehicleUpdateRequest;
import com.transport.services.IService;

public interface IVehicleService extends IService<
        VehicleCreateRequest,
        VehicleUpdateRequest,
        VehicleResponse,
        Long
> {
    // Vehicle-specific methods can be added here if needed
    // VehicleResponse findByName(String name); etc.
}