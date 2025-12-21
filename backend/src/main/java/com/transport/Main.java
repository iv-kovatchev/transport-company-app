package com.transport;

import com.transport.entities.Company;
import com.transport.utils.HibernateUtil;

import com.transport.utils.config.RouteConfig;
import com.transport.utils.config.ServerConfig;
import io.javalin.Javalin;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // Get port from environment or default to 7000
        int port = ServerConfig.getPort();

        // Create and configure Javalin server
        Javalin app = ServerConfig.createServer();

        // Register all API routes
        RouteConfig.registerRoutes(app);

        // Start the server
        app.start(port);

        // Log startup message
        System.out.println("🚀 Server started on port " + port);
        System.out.println("📍 Health check: http://localhost:" + port + "/health");
        System.out.println("📍 API base URL: http://localhost:" + port + "/api");
    }

//    private static void createDatabaseIfNotExists() {
//        String url = "jdbc:mysql://localhost:3306/?serverTimezone=Europe/Sofia";
//        String user = "root";
//        String password = "root"; // <-- ТВОЯТА ПАРОЛА ТУК
//
//        try (Connection conn = DriverManager.getConnection(url, user, password);
//             Statement stmt = conn.createStatement()) {
//
//            stmt.executeUpdate(
//                    "CREATE DATABASE IF NOT EXISTS transport_company_db " +
//                            "CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci"
//            );
//            System.out.println("✅ Database created/verified");
//
//        } catch (SQLException e) {
//            System.err.println("Failed to create database: " + e.getMessage());
//        }
//    }
}