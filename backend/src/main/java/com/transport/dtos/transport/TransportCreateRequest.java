package com.transport.dtos.transport;

import com.transport.enums.CargoType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportCreateRequest {
    @NotNull(message = "Company ID is required")
    private Long companyId;

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;

    @NotNull(message = "Driver ID is required")
    private Long driverId;

    @NotNull(message = "Cargo type is required")
    private CargoType cargoType;

    @Size(max = 255, message = "Cargo name must not exceed 255 characters")
    private String cargoName;

    @DecimalMin(value = "0.0", message = "Cargo weight must be positive")
    private BigDecimal cargoWeightKg;

    @Min(value = 0, message = "Passenger count must be positive")
    private Integer passengerCount;

    @NotBlank(message = "Start location is required")
    @Size(max = 255, message = "Start location must not exceed 255 characters")
    private String startLocation;

    @NotBlank(message = "End location is required")
    @Size(max = 255, message = "End location must not exceed 255 characters")
    private String endLocation;

    @NotNull(message = "Departure date is required")
    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be positive")
    private BigDecimal price;
}