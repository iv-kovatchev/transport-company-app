package com.transport.services.employee;

import com.transport.dtos.employee.EmployeeCreateRequest;
import com.transport.dtos.employee.EmployeeResponse;
import com.transport.dtos.employee.EmployeeUpdateRequest;
import com.transport.entities.Company;
import com.transport.entities.Employee;
import com.transport.repositories.Company.ICompanyRepository;
import com.transport.repositories.Employee.IEmployeeRepository;
import com.transport.utils.EntityMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeService implements IEmployeeService {
    private final IEmployeeRepository employeeRepository;
    private final ICompanyRepository companyRepository;

    public EmployeeService(IEmployeeRepository employeeRepository, ICompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public EmployeeResponse create(EmployeeCreateRequest request) {
        // Check if company exists
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + request.getCompanyId()));

        // Map DTO to Entity
        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .salary(request.getSalary())
                .company(company)
                .build();

        // Save to database
        Employee savedEmployee = employeeRepository.save(employee);

        // Map Entity to Response DTO
        return EntityMapper.toEmployeeResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse getById(Long id) {
        Employee employee = employeeRepository.findByIdWithCompanyAndQualifications(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));

        return EntityMapper.toEmployeeResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getAll() {
        return employeeRepository.findAllWithCompanyAndQualifications()
                .stream()
                .map(EntityMapper::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeUpdateRequest request) {
        // Check if employee exists
        Employee existingEmployee = employeeRepository.findByIdWithCompany(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));

        // Update fields (company CANNOT be changed!)
        existingEmployee.setFirstName(request.getFirstName());
        existingEmployee.setLastName(request.getLastName());
        existingEmployee.setPhone(request.getPhone());
        existingEmployee.setEmail(request.getEmail());
        existingEmployee.setSalary(request.getSalary());

        // Save updated entity
        Employee updatedEmployee = employeeRepository.update(existingEmployee);

        return EntityMapper.toEmployeeResponse(updatedEmployee);
    }

    @Override
    public void delete(Long id) {
        // Check if employee exists
        if (employeeRepository.findByIdWithCompany(id).isEmpty()) {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }

        // TODO: Later add check if employee has transports (ON DELETE RESTRICT logic)
        // if (transportRepository.existsByEmployeeId(id)) {
        //     throw new IllegalStateException("Cannot delete employee with existing transports");
        // }

        employeeRepository.delete(id);
    }
}
