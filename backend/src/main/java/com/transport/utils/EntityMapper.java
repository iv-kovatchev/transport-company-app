package com.transport.utils;

import com.transport.dtos.client.ClientResponse;
import com.transport.dtos.company.CompanyResponse;
import com.transport.dtos.employee.EmployeeResponse;
import com.transport.dtos.qualification.QualificationResponse;
import com.transport.dtos.vehicle.VehicleResponse;
import com.transport.entities.*;

import java.util.stream.Collectors;

public class EntityMapper {
    /**
     * Map Company entity to CompanyResponse DTO
     */
    public static CompanyResponse toCompanyResponse(Company company) {
        if (company == null) {
            return null;
        }

        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .registrationNumber(company.getRegistrationNumber())
                .address(company.getAddress())
                .phone(company.getPhone())
                .email(company.getEmail())
                .clients(company.getClients().stream()
                        .map(EntityMapper::toClientResponse)
                        .collect(Collectors.toList()))
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }

    /**
     * Map Client entity to ClientResponse DTO
     */
    public static ClientResponse toClientResponse(Client client) {
        if (client == null) {
            return null;
        }

        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .phone(client.getPhone())
                .email(client.getEmail())
                .address(client.getAddress())
                .companyId(client.getCompany().getId())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }

    /**
     * Map Vehicle entity to VehicleResponse DTO
     */
    public static VehicleResponse toVehicleResponse(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        return VehicleResponse.builder()
                .id(vehicle.getId())
                .licensePlate(vehicle.getLicensePlate())
                .type(vehicle.getType())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .capacityKg(vehicle.getCapacityKg())
                .capacityPassengers(vehicle.getCapacityPassengers())
                .companyId(vehicle.getCompany().getId())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .build();
    }

    /**
     * Map Employee entity to EmployeeResponse DTO
     */
    public static EmployeeResponse toEmployeeResponse(Employee employee) {
        if (employee == null) {
            return null;
        }

        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .phone(employee.getPhone())
                .email(employee.getEmail())
                .salary(employee.getSalary())
                .companyId(employee.getCompany().getId())
                .qualifications(employee.getQualifications().stream()
                        .map(EntityMapper::toQualificationResponse)
                        .collect(Collectors.toList()))
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }

    /**
     * Map Qualification entity to QualificationResponse DTO
     */
    public static QualificationResponse toQualificationResponse(Qualification qualification) {
        if (qualification == null) {
            return null;
        }

        return QualificationResponse.builder()
                .id(qualification.getId())
                .employeeId(qualification.getEmployee().getId())
                .qualificationType(qualification.getQualificationType())
                .createdAt(qualification.getCreatedAt())
                .build();
    }
}
