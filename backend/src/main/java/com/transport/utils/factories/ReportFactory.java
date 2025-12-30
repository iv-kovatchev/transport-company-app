package com.transport.utils.factories;

import com.transport.controllers.ReportController;
import com.transport.repositories.Report.IReportRepository;
import com.transport.repositories.Report.ReportRepository;
import com.transport.services.report.IReportService;
import com.transport.services.report.ReportService;

public class ReportFactory {
    private static IReportRepository reportRepository;
    private static IReportService reportService;
    private static ReportController reportController;

    public static IReportRepository getRepository() {
        if (reportRepository == null) {
            reportRepository = new ReportRepository();
        }
        return reportRepository;
    }

    public static IReportService getService() {
        if (reportService == null) {
            reportService = new ReportService(
                    getRepository(),
                    CompanyFactory.getRepository(),
                    EmployeeFactory.getRepository()
            );
        }
        return reportService;
    }

    public static ReportController getController() {
        if (reportController == null) {
            reportController = new ReportController(getService());
        }
        return reportController;
    }
}
