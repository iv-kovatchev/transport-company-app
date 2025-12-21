package com.transport.utils.config;

import io.javalin.Javalin;

/**
 * Server configuration (CORS, JSON, etc.)
 */
public class ServerConfig {

    /**
     * Create and configure Javalin instance
     */
    public static Javalin createServer() {
        return Javalin.create(config -> {
            // JSON configuration
            config.http.defaultContentType = "application/json";

            // Enable CORS for React frontend
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });

            // Disable Javalin banner in console
            config.showJavalinBanner = false;
        });
    }

    /**
     * Get port from environment variable (Azure) or default to 7000
     */
    public static int getPort() {
        String portEnv = System.getenv("PORT");
        return portEnv != null ? Integer.parseInt(portEnv) : 7000;
    }
}