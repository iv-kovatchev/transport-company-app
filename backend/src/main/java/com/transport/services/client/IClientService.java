package com.transport.services.client;

import com.transport.dtos.client.ClientCreateRequest;
import com.transport.dtos.client.ClientResponse;
import com.transport.dtos.client.ClientUpdateRequest;
import com.transport.services.IService;

public interface IClientService extends IService<
        ClientCreateRequest,
        ClientUpdateRequest,
        ClientResponse,
        Long
> {
    // Client-specific methods can be added here if needed
    // ClientResponse findByName(String name); etc.
}