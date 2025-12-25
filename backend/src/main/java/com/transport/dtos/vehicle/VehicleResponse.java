package com.transport.dtos.vehicle;

import com.transport.enums.VehicleType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {

    private Long id;
    private String licensePlate;
    private VehicleType type;
    private String brand;
    private String model;
    private Integer year;
    private BigDecimal capacityKg;
    private Integer capacityPassengers;
    private Long companyId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}