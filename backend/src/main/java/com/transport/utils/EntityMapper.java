package com.transport.utils;

import com.transport.dtos.client.ClientResponse;
import com.transport.dtos.company.CompanyResponse;
import com.transport.entities.Client;
import com.transport.entities.Company;

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
}
