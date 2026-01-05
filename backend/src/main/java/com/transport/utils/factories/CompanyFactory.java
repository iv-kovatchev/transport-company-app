package com.transport.utils.factories;

import com.transport.controllers.CompanyController;
import com.transport.repositories.Company.CompanyRepository;
import com.transport.repositories.Company.ICompanyRepository;
import com.transport.services.company.CompanyService;
import com.transport.services.company.ICompanyService;

/**
 * Factory for Company-related dependencies
 */
public class CompanyFactory {

    private static ICompanyRepository companyRepository;
    private static ICompanyService companyService;
    private static CompanyController companyController;

    public static ICompanyRepository getRepository() {
        if (companyRepository == null) {
            companyRepository = new CompanyRepository();
        }
        return companyRepository;
    }

    public static ICompanyService getService() {
        if (companyService == null) {
            companyService = new CompanyService(getRepository(),
                    EmployeeFactory.getRepository(),
                    ClientFactory.getRepository(),
                    VehicleFactory.getRepository(),
                    TransportFactory.getRepository());
        }
        return companyService;
    }

    public static CompanyController getController() {
        if (companyController == null) {
            companyController = new CompanyController(getService());
        }
        return companyController;
    }
}
