package com.transport.repositories.Report;

import com.transport.utils.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class ReportRepository implements IReportRepository {
    @Override
    public Object[] getCompanySummary(Long companyId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Get transport statistics
            String transportQuery =
                    "SELECT " +
                            "  COUNT(t.id), " +
                            "  COALESCE(SUM(t.price), 0), " +
                            "  SUM(CASE WHEN t.isPaid = true THEN 1 ELSE 0 END), " +
                            "  SUM(CASE WHEN t.isPaid = false THEN 1 ELSE 0 END), " +
                            "  COALESCE(SUM(CASE WHEN t.isPaid = true THEN t.price ELSE 0 END), 0), " +
                            "  COALESCE(SUM(CASE WHEN t.isPaid = false THEN t.price ELSE 0 END), 0) " +
                            "FROM Transport t " +
                            "WHERE t.company.id = :companyId";

            Object[] transportStats = session.createQuery(transportQuery, Object[].class)
                    .setParameter("companyId", companyId)
                    .uniqueResult();

            // Get vehicle count
            String vehicleQuery = "SELECT COUNT(v.id) FROM Vehicle v WHERE v.company.id = :companyId";
            Long vehicleCount = session.createQuery(vehicleQuery, Long.class)
                    .setParameter("companyId", companyId)
                    .uniqueResult();

            // Get employee count
            String employeeQuery = "SELECT COUNT(e.id) FROM Employee e WHERE e.company.id = :companyId";
            Long employeeCount = session.createQuery(employeeQuery, Long.class)
                    .setParameter("companyId", companyId)
                    .uniqueResult();

            // Combine results into single array
            return new Object[] {
                    transportStats[0],  // totalTransports
                    transportStats[1],  // totalRevenue
                    transportStats[2],  // paidTransports
                    transportStats[3],  // unpaidTransports
                    transportStats[4],  // paidRevenue
                    transportStats[5],  // unpaidRevenue
                    vehicleCount,       // totalVehicles
                    employeeCount       // totalEmployees
            };

        } catch (Exception e) {
            throw new RuntimeException("Error getting company summary for company: " + companyId, e);
        }
    }

    @Override
    public Object[] getCompanyRevenueByPeriod(Long companyId, LocalDate startDate, LocalDate endDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String query =
                    "SELECT " +
                            "  COALESCE(SUM(t.price), 0), " +
                            "  COALESCE(SUM(CASE WHEN t.isPaid = true THEN t.price ELSE 0 END), 0), " +
                            "  COALESCE(SUM(CASE WHEN t.isPaid = false THEN t.price ELSE 0 END), 0), " +
                            "  COUNT(t.id) " +
                            "FROM Transport t " +
                            "WHERE t.company.id = :companyId " +
                            "  AND DATE(t.departureDate) >= :startDate " +
                            "  AND DATE(t.departureDate) <= :endDate";

            return session.createQuery(query, Object[].class)
                    .setParameter("companyId", companyId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .uniqueResult();

        } catch (Exception e) {
            throw new RuntimeException("Error getting company revenue by period for company: " + companyId, e);
        }
    }

    @Override
    public List<Object[]> getDriversPerformanceByCompany(Long companyId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String query =
                    "SELECT " +
                            "  t.driver.id, " +
                            "  CONCAT(t.driver.firstName, ' ', t.driver.lastName), " +
                            "  COUNT(t.id), " +
                            "  COALESCE(SUM(t.price), 0) " +
                            "FROM Transport t " +
                            "WHERE t.company.id = :companyId " +
                            "GROUP BY t.driver.id, t.driver.firstName, t.driver.lastName " +
                            "ORDER BY SUM(t.price) DESC";

            return session.createQuery(query, Object[].class)
                    .setParameter("companyId", companyId)
                    .getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Error getting drivers performance for company: " + companyId, e);
        }
    }

    @Override
    public Object[] getEmployeeRevenue(Long employeeId, LocalDate startDate, LocalDate endDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String query =
                    "SELECT " +
                            "  t.driver.id, " +
                            "  CONCAT(t.driver.firstName, ' ', t.driver.lastName), " +
                            "  COUNT(t.id), " +
                            "  COALESCE(SUM(t.price), 0) " +
                            "FROM Transport t " +
                            "WHERE t.driver.id = :employeeId " +
                            "  AND DATE(t.departureDate) >= :startDate " +
                            "  AND DATE(t.departureDate) <= :endDate " +
                            "GROUP BY t.driver.id, t.driver.firstName, t.driver.lastName";

            return session.createQuery(query, Object[].class)
                    .setParameter("employeeId", employeeId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .uniqueResult();

        } catch (Exception e) {
            throw new RuntimeException("Error getting employee revenue for employee: " + employeeId, e);
        }
    }
}
