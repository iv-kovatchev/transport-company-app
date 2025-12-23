package com.transport;

import com.transport.utils.FlywayConfig;

import com.transport.utils.config.RouteConfig;
import com.transport.utils.config.ServerConfig;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        System.out.println("Initializing database...");
        FlywayConfig.migrate();

        // Get port from environment or default to 7000
        int port = ServerConfig.getPort();

        // Create and configure Javalin server
        Javalin app = ServerConfig.createServer();

        // Register all API routes
        RouteConfig.registerRoutes(app);

        // Start the server
        app.start(port);

        // Log startup message
        System.out.println("Server started on port " + port);
        System.out.println("Health check: http://localhost:" + port + "/health");
        System.out.println("API base URL: http://localhost:" + port + "/api");
    }
}