package com.transport.dtos.transport;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime departureDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalDate;

    private BigDecimal price;
    private Boolean isPaid;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime paymentDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}