package com.transport.services.transport;

import com.transport.dtos.transport.TransportCreateRequest;
import com.transport.dtos.transport.TransportResponse;
import com.transport.dtos.transport.TransportUpdateRequest;
import com.transport.services.IService;

public interface ITransportService extends IService<
        TransportCreateRequest,
        TransportUpdateRequest,
        TransportResponse,
        Long
> {
    /**
     * Mark transport as paid
     */
    TransportResponse markAsPaid(Long id);
}
