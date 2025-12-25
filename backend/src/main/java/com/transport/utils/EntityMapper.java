package com.transport.utils;

import com.transport.dtos.client.ClientResponse;
import com.transport.dtos.company.CompanyResponse;
import com.transport.dtos.vehicle.VehicleResponse;
import com.transport.entities.Client;
import com.transport.entities.Company;
import com.transport.entities.Vehicle;

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
}
