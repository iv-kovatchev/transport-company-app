package com.transport.repositories.Employee;

import com.transport.entities.Employee;
import com.transport.repositories.IRepository;

import java.util.List;
import java.util.Optional;

public interface IEmployeeRepository extends IRepository<Employee, Long> {
    /**
     * Find employee by ID with company data (JOIN FETCH to avoid N+1)
     */
    Optional<Employee> findByIdWithCompany(Long id);

    /**
     * Find all employees with company AND qualifications (JOIN FETCH to avoid N+1)
     */
    List<Employee> findAllWithCompanyAndQualifications();

    /**
     * Find employee by ID with qualifications (JOIN FETCH to avoid N+1)
     */
    Optional<Employee> findByIdWithQualifications(Long id);

    /**
     * Find employee by ID with company AND qualifications (JOIN FETCH to avoid N+1)
     */
    Optional<Employee> findByIdWithCompanyAndQualifications(Long id);

    List<Employee> findByCompanyId(Long companyId);
}
