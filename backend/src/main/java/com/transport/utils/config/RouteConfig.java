package com.transport.utils.config;

import com.transport.utils.factories.ClientFactory;
import com.transport.utils.factories.CompanyFactory;
import com.transport.utils.factories.VehicleFactory;
import io.javalin.Javalin;

/**
 * Centralized route configuration
 */
public class RouteConfig {

    /**
     * Register all API routes
     */
    public static void registerRoutes(Javalin app) {
        registerCompanyRoutes(app);
        registerClientRoutes(app);
        registerVehicleRoutes(app);

        // TODO: Add other routes as entities are completed
        // registerVehicleRoutes(app);
        // registerEmployeeRoutes(app);
        // registerTransportRoutes(app);
    }

    /**
     * Register Company endpoints
     */
    private static void registerCompanyRoutes(Javalin app) {
        var controller = CompanyFactory.getController();

        app.post("/api/companies", controller::create);
        app.get("/api/companies", controller::getAll);
        app.get("/api/companies/{id}", controller::getById);
        app.put("/api/companies/{id}", controller::update);
        app.delete("/api/companies/{id}", controller::delete);
    }

    /**
     * Register Client endpoints
     */
    private static void registerClientRoutes(Javalin app) {
        var controller = ClientFactory.getController();

        app.post("/api/clients", controller::create);
        app.get("/api/clients", controller::getAll);
        app.get("/api/clients/{id}", controller::getById);
        app.put("/api/clients/{id}", controller::update);
        app.delete("/api/clients/{id}", controller::delete);
    }

    /**
     * Register Vehicle endpoints
     */
    private static void registerVehicleRoutes(Javalin app) {
        var controller = VehicleFactory.getController();

        app.post("/api/vehicles", controller::create);
        app.get("/api/vehicles", controller::getAll);
        app.get("/api/vehicles/{id}", controller::getById);
        app.put("/api/vehicles/{id}", controller::update);
        app.delete("/api/vehicles/{id}", controller::delete);
    }
}
