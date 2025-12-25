package com.transport.dtos.vehicle;

import com.transport.enums.VehicleType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleCreateRequest {

    @NotBlank(message = "License plate is required")
    @Size(max = 20, message = "License plate must not exceed 20 characters")
    private String licensePlate;

    @NotNull(message = "Vehicle type is required")
    private VehicleType type;

    @Size(max = 100, message = "Brand must not exceed 100 characters")
    private String brand;

    @Size(max = 100, message = "Model must not exceed 100 characters")
    private String model;

    @Min(value = 1900, message = "Year must be 1900 or later")
    @Max(value = 2100, message = "Year must be 2100 or earlier")
    private Integer year;

    @DecimalMin(value = "0.0", message = "Capacity in kg must be positive")
    private BigDecimal capacityKg;

    @Min(value = 0, message = "Passenger capacity must be positive")
    private Integer capacityPassengers;

    @NotNull(message = "Company ID is required")
    private Long companyId;
}