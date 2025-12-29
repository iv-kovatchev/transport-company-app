package com.transport.services.employee;

import com.transport.dtos.employee.EmployeeCreateRequest;
import com.transport.dtos.employee.EmployeeResponse;
import com.transport.dtos.employee.EmployeeUpdateRequest;
import com.transport.services.IService;

public interface IEmployeeService extends IService<
        EmployeeCreateRequest,
        EmployeeUpdateRequest,
        EmployeeResponse,
        Long
> {

    // Employee-specific methods can be added here if needed
}
