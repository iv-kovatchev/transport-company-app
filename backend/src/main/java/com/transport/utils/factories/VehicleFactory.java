package com.transport.utils.factories;

import com.transport.controllers.VehicleController;
import com.transport.repositories.Vehicle.IVehicleRepository;
import com.transport.repositories.Vehicle.VehicleRepository;
import com.transport.services.vehicle.IVehicleService;
import com.transport.services.vehicle.VehicleService;

public class VehicleFactory {

    private static IVehicleRepository vehicleRepository;
    private static IVehicleService vehicleService;
    private static VehicleController vehicleController;

    public static IVehicleRepository getRepository() {
        if (vehicleRepository == null) {
            vehicleRepository = new VehicleRepository();
        }
        return vehicleRepository;
    }

    public static IVehicleService getService() {
        if (vehicleService == null) {
            vehicleService = new VehicleService(getRepository(), CompanyFactory.getRepository());
        }
        return vehicleService;
    }

    public static VehicleController getController() {
        if (vehicleController == null) {
            vehicleController = new VehicleController(getService());
        }
        return vehicleController;
    }
}
