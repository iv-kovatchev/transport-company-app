package com.transport.dtos.client;

import com.transport.dtos.company.CompanyResponse;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Long companyId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
