package com.transport.services.company;

import com.transport.dtos.company.CompanyCreateRequest;
import com.transport.dtos.company.CompanyResponse;
import com.transport.dtos.company.CompanyUpdateRequest;
import com.transport.services.IService;

public interface ICompanyService extends IService<
        CompanyCreateRequest,
        CompanyUpdateRequest,
        CompanyResponse,
        Long
> {
    // Company-specific methods can be added here if needed
    // CompanyResponse findByName(String name); etc.
}
