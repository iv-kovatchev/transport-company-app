package com.transport.utils.factories;

import com.transport.controllers.EmployeeController;
import com.transport.repositories.Employee.EmployeeRepository;
import com.transport.repositories.Employee.IEmployeeRepository;
import com.transport.services.employee.EmployeeService;
import com.transport.services.employee.IEmployeeService;

public class EmployeeFactory {
    private static IEmployeeRepository employeeRepository;
    private static IEmployeeService employeeService;
    private static EmployeeController employeeController;

    public static IEmployeeRepository getRepository() {
        if (employeeRepository == null) {
            employeeRepository = new EmployeeRepository();
        }
        return employeeRepository;
    }

    public static IEmployeeService getService() {
        if (employeeService == null) {
            employeeService = new EmployeeService(
                    getRepository(),
                    CompanyFactory.getRepository()
            );
        }
        return employeeService;
    }

    public static EmployeeController getController() {
        if (employeeController == null) {
            employeeController = new EmployeeController(getService());
        }
        return employeeController;
    }
}
