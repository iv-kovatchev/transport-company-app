package com.transport.utils.config;

import com.transport.utils.factories.CompanyFactory;
import io.javalin.Javalin;

/**
 * Centralized route configuration
 */
public class RouteConfig {

    /**
     * Register all API routes
     */
    public static void registerRoutes(Javalin app) {
        // Company routes
        registerCompanyRoutes(app);

        // TODO: Add other routes as entities are completed
        // registerClientRoutes(app);
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
}
