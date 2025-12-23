package com.transport.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

            // Get database credentials (prioritize .env, fallback to system env)
            String dbUrl = getEnvVar(dotenv, "DB_URL");
            String dbUsername = getEnvVar(dotenv, "DB_USERNAME");
            String dbPassword = getEnvVar(dotenv, "DB_PASSWORD");

            if (dbUrl == null || dbUrl.isEmpty()) {
                throw new RuntimeException("DB_URL not configured! Create .env file or set environment variables.");
            }

            if (!dbUrl.contains("createDatabaseIfNotExist")) {
                dbUrl += (dbUrl.contains("?") ? "&" : "?") + "createDatabaseIfNotExist=true";
            }

            System.out.println("Connected to: " + (dotenv.get("DB_URL") != null ? "LOCAL database" : "AZURE database"));

            // Build configuration using HibernateConfig
            Configuration configuration = HibernateConfig.buildConfiguration(dbUrl, dbUsername, dbPassword);

            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static String getEnvVar(Dotenv dotenv, String key) {
        String value = dotenv.get(key);
        if (value == null) {
            value = System.getenv(key);
        }
        return value;
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}