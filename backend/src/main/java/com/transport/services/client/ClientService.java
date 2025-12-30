package com.transport.services.client;

import com.transport.dtos.client.ClientCreateRequest;
import com.transport.dtos.client.ClientResponse;
import com.transport.dtos.client.ClientUpdateRequest;
import com.transport.entities.Client;
import com.transport.entities.Company;
import com.transport.repositories.Client.IClientRepository;
import com.transport.repositories.Company.ICompanyRepository;
import com.transport.utils.EntityMapper;
import com.transport.utils.factories.TransportFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ClientService implements IClientService {
    private final IClientRepository clientRepository;
    private final ICompanyRepository companyRepository;

    public ClientService(IClientRepository clientRepository, ICompanyRepository companyRepository) {
        this.clientRepository = clientRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public ClientResponse create(ClientCreateRequest request) {
        // Check if company exists
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + request.getCompanyId()));

        // Check uniqueness (name within company)
        if (clientRepository.existsByNameAndCompany(request.getName(), request.getCompanyId())) {
            throw new IllegalArgumentException("Client with name '" + request.getName() + "' already exists in this company");
        }

        // Map DTO to Entity
        Client client = Client.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .company(company)
                .build();

        // Save to database
        Client savedClient = clientRepository.save(client);

        // Map Entity to Response DTO
        return EntityMapper.toClientResponse(savedClient);
    }

    @Override
    public ClientResponse getById(Long id) {
        Client client = clientRepository.findByIdWithCompany(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + id));

        return EntityMapper.toClientResponse(client);
    }

    @Override
    public List<ClientResponse> getAll() {
        return clientRepository.findAllWithCompany()
                .stream()
                .map(EntityMapper::toClientResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponse update(Long id, ClientUpdateRequest request) {
        // Check if client exists
        Client existingClient = clientRepository.findByIdWithCompany(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + id));

        // Check name uniqueness within company (if name is being changed)
        if (!existingClient.getName().equals(request.getName())
                && clientRepository.existsByNameAndCompany(request.getName(), existingClient.getCompany().getId())) {
            throw new IllegalArgumentException("Client with name '" + request.getName() + "' already exists in this company");
        }

        // Update fields (company cannot be changed!)
        existingClient.setName(request.getName());
        existingClient.setPhone(request.getPhone());
        existingClient.setEmail(request.getEmail());
        existingClient.setAddress(request.getAddress());

        // Save updated entity
        Client updatedClient = clientRepository.update(existingClient);

        return EntityMapper.toClientResponse(updatedClient);
    }

    @Override
    public void delete(Long id) {
        // Check if client exists
        if (clientRepository.findByIdWithCompany(id).isEmpty()) {
            throw new IllegalArgumentException("Client not found with id: " + id);
        }

        if (TransportFactory.getRepository().existsByClientId(id)) {
            throw new IllegalStateException("Cannot delete client with existing transports");
        }

        clientRepository.delete(id);
    }
}
