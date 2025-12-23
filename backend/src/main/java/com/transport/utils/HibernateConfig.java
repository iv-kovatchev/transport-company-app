package com.transport.utils;

import org.hibernate.cfg.Configuration;

public class HibernateConfig {

    public static Configuration buildConfiguration(String dbUrl, String dbUsername, String dbPassword) {
        Configuration configuration = new Configuration();

        // Database connection settings
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", dbUrl);
        configuration.setProperty("hibernate.connection.username", dbUsername);
        configuration.setProperty("hibernate.connection.password", dbPassword);

        // Hibernate behavior settings
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");

        configuration.setProperty("javax.persistence.validation.mode", "CALLBACK");
        configuration.setProperty("hibernate.validator.apply_to_ddl", "false");

        // Connection pool settings (C3P0)
        configuration.setProperty("hibernate.c3p0.min_size", "5");
        configuration.setProperty("hibernate.c3p0.max_size", "20");
        configuration.setProperty("hibernate.c3p0.timeout", "300");
        configuration.setProperty("hibernate.c3p0.max_statements", "50");

        // Register entity classes
        registerEntities(configuration);

        return configuration;
    }

    private static void registerEntities(Configuration configuration) {
        configuration.addAnnotatedClass(com.transport.entities.Company.class);
        configuration.addAnnotatedClass(com.transport.entities.Client.class);

        System.out.println("Registered: Client");
        // TODO: Add more entities here as we create them
        // configuration.addAnnotatedClass(com.transport.entities.Client.class);
        // configuration.addAnnotatedClass(com.transport.entities.Vehicle.class);
        // etc.
    }
}