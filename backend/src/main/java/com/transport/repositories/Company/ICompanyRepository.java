package com.transport.repositories.Company;

import com.transport.entities.Company;
import com.transport.repositories.IRepository;

public interface ICompanyRepository extends IRepository<Company, Long> {
    //Check if a company with the given name already exists
    boolean existsByName(String name);

    //Check if a company with the given registration number already exists
    boolean existsByRegistrationNumber(String registrationNumber);
}
