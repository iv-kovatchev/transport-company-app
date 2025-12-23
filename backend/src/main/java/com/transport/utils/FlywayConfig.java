package com.transport.utils;

import org.flywaydb.core.Flyway;
import io.github.cdimascio.dotenv.Dotenv;

public class FlywayConfig {
    public static void migrate() {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

            // Get database credentials
            String dbUrl = getEnvVar(dotenv, "DB_URL");
            String dbUsername = getEnvVar(dotenv, "DB_USERNAME");
            String dbPassword = getEnvVar(dotenv, "DB_PASSWORD");

            if (dbUrl == null || dbUrl.isEmpty()) {
                throw new RuntimeException("DB_URL not configured!");
            }

            if (!dbUrl.contains("createDatabaseIfNotExist")) {
                dbUrl += (dbUrl.contains("?") ? "&" : "?") + "createDatabaseIfNotExist=true";
            }

            System.out.println("Running Flyway migrations...");

            // Configure and run Flyway
            Flyway flyway = Flyway.configure()
                    .dataSource(dbUrl, dbUsername, dbPassword)
                    .locations("classpath:db/migrations")
                    .baselineOnMigrate(true)
                    .load();

            flyway.migrate();

            System.out.println("Flyway migrations completed successfully!");
        } catch (Exception e) {
            System.err.println("Flyway migration failed: " + e.getMessage());
            throw new RuntimeException("Database migration failed", e);
        }
    }

    private static String getEnvVar(Dotenv dotenv, String key) {
        String value = dotenv.get(key);
        if (value == null) {
            value = System.getenv(key);
        }
        return value;
    }
}
