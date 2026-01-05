package com.transport.services.company;

import com.transport.dtos.client.ClientResponse;
import com.transport.dtos.company.CompanyCreateRequest;
import com.transport.dtos.company.CompanyResponse;
import com.transport.dtos.company.CompanyUpdateRequest;
import com.transport.dtos.employee.EmployeeResponse;
import com.transport.dtos.transport.TransportResponse;
import com.transport.dtos.vehicle.VehicleResponse;
import com.transport.entities.*;
import com.transport.repositories.Client.IClientRepository;
import com.transport.repositories.Company.ICompanyRepository;
import com.transport.repositories.Employee.IEmployeeRepository;
import com.transport.repositories.Transport.ITransportRepository;
import com.transport.repositories.Vehicle.IVehicleRepository;
import com.transport.utils.EntityMapper;
import com.transport.utils.factories.EmployeeFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyService implements ICompanyService {
    private final ICompanyRepository companyRepository;
    private final IEmployeeRepository employeeRepository;
    private final IClientRepository clientRepository;
    private final IVehicleRepository vehicleRepository;
    private final ITransportRepository transportRepository;

    public CompanyService(
            ICompanyRepository companyRepository,
            IEmployeeRepository employeeRepository,
            IClientRepository clientRepository,
            IVehicleRepository vehicleRepository,
            ITransportRepository transportRepository
    ) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
        this.vehicleRepository = vehicleRepository;
        this.transportRepository = transportRepository;
    }

    @Override
    public CompanyResponse create(CompanyCreateRequest request) {
        if (companyRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Company with name " + request.getName() + " already exists");
        }

        if (request.getRegistrationNumber() != null && !request.getRegistrationNumber().isEmpty()) {
            if (companyRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
                throw new IllegalArgumentException("Company with registration number '" + request.getRegistrationNumber() + "' already exists");
            }
        }

        //Map DTO to Entity
        Company company = Company.builder()
                .name(request.getName())
                .registrationNumber(request.getRegistrationNumber())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();

        //Save to db
        Company savedCompany = companyRepository.save(company);

        // Map Entity to Response DTO
        return EntityMapper.toCompanyResponse(savedCompany);
    }

    @Override
    public CompanyResponse getById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + id));

        return EntityMapper.toCompanyResponse(company);
    }

    @Override
    public List<CompanyResponse> getAll() {
        return companyRepository.findAll()
                .stream()
                .map(EntityMapper::toCompanyResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyResponse update(Long id, CompanyUpdateRequest request) {
        // Check if company exists
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + id));

        // Check name uniqueness (if name is being changed)
        if (!existingCompany.getName().equals(request.getName())
                && companyRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Company with name '" + request.getName() + "' already exists");
        }

        // Check registration number uniqueness (if registration number is being changed)
        if (request.getRegistrationNumber() != null
                && !request.getRegistrationNumber().equals(existingCompany.getRegistrationNumber())
                && companyRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new IllegalArgumentException("Company with registration number '" + request.getRegistrationNumber() + "' already exists");
        }

        // Update fields
        existingCompany.setName(request.getName());
        existingCompany.setRegistrationNumber(request.getRegistrationNumber());
        existingCompany.setAddress(request.getAddress());
        existingCompany.setPhone(request.getPhone());
        existingCompany.setEmail(request.getEmail());

        // Save updated entity
        Company updatedCompany = companyRepository.update(existingCompany);

        return EntityMapper.toCompanyResponse(updatedCompany);
    }

    @Override
    public void delete(Long id) {
        // Check if company exists
        if (companyRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Company not found with id: " + id);
        }

        companyRepository.delete(id);
    }

    @Override
    public List<EmployeeResponse> getCompanyEmployees(Long companyId) {
        // Check if company exists
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + companyId));

        // Fetch employees separately
        List<Employee> employees = employeeRepository.findByCompanyId(companyId);

        return employees.stream()
                .map(EntityMapper::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientResponse> getCompanyClients(Long companyId) {
        // Check if company exists
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + companyId));

        // Fetch clients separately
        List<Client> clients = clientRepository.findByCompanyId(companyId);

        return clients.stream()
                .map(EntityMapper::toClientResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleResponse> getCompanyVehicles(Long companyId) {
        // Check if company exists
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + companyId));

        // Fetch vehicles separately
        List<Vehicle> vehicles = vehicleRepository.findByCompanyId(companyId);

        return vehicles.stream()
                .map(EntityMapper::toVehicleResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransportResponse> getCompanyTransports(Long companyId) {
        // Check if company exists
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + companyId));

        // Fetch transports separately
        List<Transport> transports = transportRepository.findByCompanyId(companyId);

        return transports.stream()
                .map(EntityMapper::toTransportResponse)
                .collect(Collectors.toList());
    }
}
