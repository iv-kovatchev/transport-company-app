package com.transport.services.report;

import com.transport.dtos.report.CompanySummaryReport;
import com.transport.dtos.report.DriverPerformanceReport;
import com.transport.dtos.report.EmployeeRevenueReport;
import com.transport.dtos.report.RevenueByPeriodReport;
import com.transport.entities.Company;
import com.transport.entities.Employee;
import com.transport.repositories.Company.ICompanyRepository;
import com.transport.repositories.Report.IReportRepository;
import com.transport.repositories.Employee.IEmployeeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportService implements IReportService {
    private final IReportRepository reportRepository;
    private final ICompanyRepository companyRepository;
    private final IEmployeeRepository employeeRepository;

    public ReportService(IReportRepository reportRepository,
                         ICompanyRepository companyRepository,
                         IEmployeeRepository employeeRepository) {
        this.reportRepository = reportRepository;
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public CompanySummaryReport getCompanySummary(Long companyId) {
        // Check if company exists
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + companyId));

        // Get aggregated data
        Object[] data = reportRepository.getCompanySummary(companyId);

        // Map to DTO
        return CompanySummaryReport.builder()
                .companyId(companyId)
                .companyName(company.getName())
                .totalTransports(data[0] != null ? ((Number) data[0]).longValue() : 0L)
                .totalRevenue(data[1] != null ? (BigDecimal) data[1] : BigDecimal.ZERO)
                .paidTransports(data[2] != null ? ((Number) data[2]).longValue() : 0L)
                .unpaidTransports(data[3] != null ? ((Number) data[3]).longValue() : 0L)
                .paidRevenue(data[4] != null ? (BigDecimal) data[4] : BigDecimal.ZERO)
                .unpaidRevenue(data[5] != null ? (BigDecimal) data[5] : BigDecimal.ZERO)
                .totalVehicles(data[6] != null ? (Long) data[6] : 0L)
                .totalEmployees(data[7] != null ? (Long) data[7] : 0L)
                .build();
    }

    @Override
    public RevenueByPeriodReport getCompanyRevenueByPeriod(Long companyId, LocalDate startDate, LocalDate endDate) {
        // Validate company exists
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + companyId));

        // Validate date range
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        // Get aggregated data
        Object[] data = reportRepository.getCompanyRevenueByPeriod(companyId, startDate, endDate);

        // Map to DTO
        return RevenueByPeriodReport.builder()
                .companyId(companyId)
                .companyName(company.getName())
                .startDate(startDate)
                .endDate(endDate)
                .totalRevenue((BigDecimal) data[0])
                .paidRevenue((BigDecimal) data[1])
                .unpaidRevenue((BigDecimal) data[2])
                .transportsCount(((Number) data[3]).longValue())
                .build();
    }

    @Override
    public List<DriverPerformanceReport> getDriversPerformance(Long companyId) {
        // Validate company exists
        if (companyRepository.findById(companyId).isEmpty()) {
            throw new IllegalArgumentException("Company not found with id: " + companyId);
        }

        // Get aggregated data
        List<Object[]> dataList = reportRepository.getDriversPerformanceByCompany(companyId);

        // Map to DTOs
        List<DriverPerformanceReport> reports = new ArrayList<>();
        for (Object[] data : dataList) {
            Long driverId = ((Number) data[0]).longValue();
            String driverName = (String) data[1];
            Long totalTransports = ((Number) data[2]).longValue();
            BigDecimal totalRevenue = (BigDecimal) data[3];

            // Calculate average revenue per transport
            BigDecimal averageRevenue = BigDecimal.ZERO;
            if (totalTransports > 0) {
                averageRevenue = totalRevenue.divide(
                        BigDecimal.valueOf(totalTransports),
                        2,
                        RoundingMode.HALF_UP
                );
            }

            reports.add(DriverPerformanceReport.builder()
                    .driverId(driverId)
                    .driverName(driverName)
                    .totalTransports(totalTransports)
                    .totalRevenue(totalRevenue)
                    .averageRevenuePerTransport(averageRevenue)
                    .build());
        }

        return reports;
    }

    @Override
    public EmployeeRevenueReport getEmployeeRevenue(Long employeeId, LocalDate startDate, LocalDate endDate) {
        // Validate employee exists
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + employeeId));

        // Validate date range
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        // Get aggregated data
        Object[] data = reportRepository.getEmployeeRevenue(employeeId, startDate, endDate);

        // Handle case when employee has no transports in period
        if (data == null) {
            return EmployeeRevenueReport.builder()
                    .employeeId(employeeId)
                    .employeeName(employee.getFirstName() + " " + employee.getLastName())
                    .startDate(startDate)
                    .endDate(endDate)
                    .totalTransports(0L)
                    .totalRevenue(BigDecimal.ZERO)
                    .averageRevenuePerTransport(BigDecimal.ZERO)
                    .build();
        }

        // Map to DTO
        Long totalTransports = ((Number) data[2]).longValue();
        BigDecimal totalRevenue = (BigDecimal) data[3];

        // Calculate average
        BigDecimal averageRevenue = BigDecimal.ZERO;
        if (totalTransports > 0) {
            averageRevenue = totalRevenue.divide(
                    BigDecimal.valueOf(totalTransports),
                    2,
                    RoundingMode.HALF_UP
            );
        }

        return EmployeeRevenueReport.builder()
                .employeeId(employeeId)
                .employeeName((String) data[1])
                .startDate(startDate)
                .endDate(endDate)
                .totalTransports(totalTransports)
                .totalRevenue(totalRevenue)
                .averageRevenuePerTransport(averageRevenue)
                .build();
    }
}
