package com.transport;

import com.transport.entities.Company;
import com.transport.utils.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        //createDatabaseIfNotExists();

        System.out.println("Testing Hibernate connection...");

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Create test company
            Company company = Company.builder()
                    .name("Test Transport 2 Ltd")
                    .registrationNumber("BG123456781")
                    .address("Sofia, Bulgaria")
                    .phone("+359888123456")
                    .email("info@testtransport.bg")
                    .build();

            session.persist(company);
            transaction.commit();

            System.out.println("✅ Company saved successfully! ID: " + company.getId());

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
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