package com.transport.services.company;

import com.transport.dtos.company.CompanyCreateRequest;
import com.transport.dtos.company.CompanyResponse;
import com.transport.dtos.company.CompanyUpdateRequest;
import com.transport.entities.Company;
import com.transport.repositories.Company.ICompanyRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyService implements ICompanyService {
    public final ICompanyRepository companyRepository;

    public CompanyService(ICompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
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
        return mapToResponse(savedCompany);
    }

    @Override
    public CompanyResponse getById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + id));

        return mapToResponse(company);
    }

    @Override
    public List<CompanyResponse> getAll() {
        return companyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
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

        return mapToResponse(updatedCompany);
    }

    @Override
    public void delete(Long id) {
        // Check if company exists
        if (companyRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Company not found with id: " + id);
        }

        companyRepository.delete(id);
    }

    /**
     * Map Company entity to CompanyResponse DTO
     */
    private CompanyResponse mapToResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .registrationNumber(company.getRegistrationNumber())
                .address(company.getAddress())
                .phone(company.getPhone())
                .email(company.getEmail())
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }
}
