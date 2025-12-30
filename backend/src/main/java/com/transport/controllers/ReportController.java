package com.transport.controllers;

import com.transport.dtos.report.CompanySummaryReport;
import com.transport.dtos.report.DriverPerformanceReport;
import com.transport.dtos.report.EmployeeRevenueReport;
import com.transport.dtos.report.RevenueByPeriodReport;
import com.transport.services.report.IReportService;
import com.transport.utils.ErrorHandler;
import com.transport.utils.ErrorHandler.ErrorResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ReportController {
    private final IReportService reportService;

    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * GET /api/reports/companies/{id}/summary
     */
    public void getCompanySummary(Context ctx) {
        try {
            Long companyId = Long.parseLong(ctx.pathParam("id"));
            CompanySummaryReport report = reportService.getCompanySummary(companyId);
            ctx.json(report);
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorHandler.ErrorResponse("Invalid company ID format"));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/reports/companies/{id}/revenue?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD
     */
    public void getCompanyRevenueByPeriod(Context ctx) {
        try {
            Long companyId = Long.parseLong(ctx.pathParam("id"));

            // Parse query parameters
            String startDateStr = ctx.queryParam("startDate");
            String endDateStr = ctx.queryParam("endDate");

            if (startDateStr == null || endDateStr == null) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("Both startDate and endDate query parameters are required"));
                return;
            }

            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            RevenueByPeriodReport report = reportService.getCompanyRevenueByPeriod(companyId, startDate, endDate);
            ctx.json(report);

        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Invalid company ID format"));
        } catch (DateTimeParseException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(new ErrorResponse("Invalid date format. Use YYYY-MM-DD (e.g., 2025-01-01)"));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/reports/companies/{id}/drivers-performance
     */
    public void getDriversPerformance(Context ctx) {
        try {
            Long companyId = Long.parseLong(ctx.pathParam("id"));
            List<DriverPerformanceReport> reports = reportService.getDriversPerformance(companyId);
            ctx.json(reports);
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Invalid company ID format"));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/reports/employees/{id}/revenue?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD
     */
    public void getEmployeeRevenue(Context ctx) {
        try {
            Long employeeId = Long.parseLong(ctx.pathParam("id"));

            // Parse query parameters
            String startDateStr = ctx.queryParam("startDate");
            String endDateStr = ctx.queryParam("endDate");

            if (startDateStr == null || endDateStr == null) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("Both startDate and endDate query parameters are required"));
                return;
            }

            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            EmployeeRevenueReport report = reportService.getEmployeeRevenue(employeeId, startDate, endDate);
            ctx.json(report);

        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Invalid employee ID format"));
        } catch (DateTimeParseException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(new ErrorResponse("Invalid date format. Use YYYY-MM-DD (e.g., 2025-01-01)"));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }
}
