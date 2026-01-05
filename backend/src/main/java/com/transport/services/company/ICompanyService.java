package com.transport.services.company;

import com.transport.dtos.client.ClientResponse;
import com.transport.dtos.company.CompanyCreateRequest;
import com.transport.dtos.company.CompanyResponse;
import com.transport.dtos.company.CompanyUpdateRequest;
import com.transport.dtos.employee.EmployeeResponse;
import com.transport.dtos.transport.TransportResponse;
import com.transport.dtos.vehicle.VehicleResponse;
import com.transport.services.IService;

import java.util.List;

public interface ICompanyService extends IService<
        CompanyCreateRequest,
        CompanyUpdateRequest,
        CompanyResponse,
        Long
> {
    List<EmployeeResponse> getCompanyEmployees(Long companyId);

    List<ClientResponse> getCompanyClients(Long companyId);

    List<VehicleResponse> getCompanyVehicles(Long companyId);

    List<TransportResponse> getCompanyTransports(Long companyId);
}
