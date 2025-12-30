package com.transport.repositories.Report;

import java.time.LocalDate;

import java.util.List;

public interface IReportRepository {
    /**
     * Get company summary statistics
     */
    Object[] getCompanySummary(Long companyId);

    /**
     * Get company revenue by period
     */
    Object[] getCompanyRevenueByPeriod(Long companyId, LocalDate startDate, LocalDate endDate);

    /**
     * Get drivers performance for a company
     */
    List<Object[]> getDriversPerformanceByCompany(Long companyId);

    /**
     * Get employee revenue for a period
     */
    Object[] getEmployeeRevenue(Long employeeId, LocalDate startDate, LocalDate endDate);
}
