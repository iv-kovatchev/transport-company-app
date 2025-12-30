package com.transport.services.report;

import com.transport.dtos.report.CompanySummaryReport;
import com.transport.dtos.report.DriverPerformanceReport;
import com.transport.dtos.report.EmployeeRevenueReport;
import com.transport.dtos.report.RevenueByPeriodReport;

import java.time.LocalDate;
import java.util.List;

public interface IReportService {
    /**
     * Get comprehensive summary for a company
     */
    CompanySummaryReport getCompanySummary(Long companyId);

    /**
     * Get company revenue by date period
     */
    RevenueByPeriodReport getCompanyRevenueByPeriod(Long companyId, LocalDate startDate, LocalDate endDate);

    /**
     * Get all drivers performance for a company (sorted by revenue DESC)
     */
    List<DriverPerformanceReport> getDriversPerformance(Long companyId);

    /**
     * Get revenue report for specific employee (driver)
     */
    EmployeeRevenueReport getEmployeeRevenue(Long employeeId, LocalDate startDate, LocalDate endDate);
}
