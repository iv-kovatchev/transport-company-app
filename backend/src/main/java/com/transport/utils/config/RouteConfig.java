package com.transport.utils.config;

import com.transport.utils.factories.*;
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
        registerEmployeeRoutes(app);
        registerQualificationRoutes(app);
        registerTransportRoutes(app);
        registerReportRoutes(app);
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

        app.get("/api/companies/{id}/employees", controller::getCompanyEmployees);
        app.get("/api/companies/{id}/clients", controller::getCompanyClients);
        app.get("/api/companies/{id}/vehicles", controller::getCompanyVehicles);
        app.get("/api/companies/{id}/transports", controller::getCompanyTransports);
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

    /**
     * Register Employee endpoints
     */
    private static void registerEmployeeRoutes(Javalin app) {
        var controller = EmployeeFactory.getController();

        app.post("/api/employees", controller::create);
        app.get("/api/employees", controller::getAll);
        app.get("/api/employees/{id}", controller::getById);
        app.put("/api/employees/{id}", controller::update);
        app.delete("/api/employees/{id}", controller::delete);
    }

    /**
     * Register Qualification endpoints
     */
    private static void registerQualificationRoutes(Javalin app) {
        var controller = QualificationFactory.getController();

        app.post("/api/qualifications", controller::create);
        app.get("/api/employees/{employeeId}/qualifications", controller::getByEmployeeId);
        app.delete("/api/employees/{employeeId}/qualifications/{qualificationType}", controller::deleteByEmployeeIdAndType);
        app.delete("/api/qualifications/{id}", controller::delete);
    }

    /**
     * Register Transport endpoints
     */
    private static void registerTransportRoutes(Javalin app) {
        var controller = TransportFactory.getController();

        app.post("/api/transports", controller::create);
        app.get("/api/transports", controller::getAll);
        app.get("/api/transports/export/csv", controller::exportCsv);
        app.get("/api/transports/{id}", controller::getById);
        app.put("/api/transports/{id}", controller::update);
        app.delete("/api/transports/{id}", controller::delete);
        app.put("/api/transports/{id}/mark-paid", controller::markAsPaid);
    }

    /**
     * Register Report endpoints
     */
    private static void registerReportRoutes(Javalin app) {
        var controller = ReportFactory.getController();

        app.get("/api/reports/companies/{id}/summary", controller::getCompanySummary);
        app.get("/api/reports/companies/{id}/revenue", controller::getCompanyRevenueByPeriod);
        app.get("/api/reports/companies/{id}/drivers-performance", controller::getDriversPerformance);
        app.get("/api/reports/employees/{id}/revenue", controller::getEmployeeRevenue);
    }
}
