package com.transport.dtos.transport;

import com.transport.enums.CargoType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportResponse {
    private Long id;
    private Long companyId;
    private Long clientId;
    private Long vehicleId;
    private Long driverId;
    private CargoType cargoType;
    private String cargoName;
    private BigDecimal cargoWeightKg;
    private Integer passengerCount;
    private String startLocation;
    private String endLocation;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private BigDecimal price;
    private Boolean isPaid;
    private LocalDateTime paymentDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}