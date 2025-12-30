package com.transport.utils.factories;

import com.transport.controllers.TransportController;
import com.transport.repositories.Transport.ITransportRepository;
import com.transport.repositories.Transport.TransportRepository;
import com.transport.services.transport.ITransportService;
import com.transport.services.transport.TransportService;

public class TransportFactory {
    private static ITransportRepository transportRepository;
    private static ITransportService transportService;
    private static TransportController transportController;

    public static ITransportRepository getRepository() {
        if (transportRepository == null) {
            transportRepository = new TransportRepository();
        }
        return transportRepository;
    }

    public static ITransportService getService() {
        if (transportService == null) {
            transportService = new TransportService(
                    getRepository(),
                    CompanyFactory.getRepository(),
                    ClientFactory.getRepository(),
                    VehicleFactory.getRepository(),
                    EmployeeFactory.getRepository()
            );
        }
        return transportService;
    }

    public static TransportController getController() {
        if (transportController == null) {
            transportController = new TransportController(getService());
        }
        return transportController;
    }
}
