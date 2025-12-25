package com.transport.entities;

import com.transport.enums.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "vehicles",
        indexes = {
                @Index(name = "idx_vehicle_company_id", columnList = "company_id"),
                @Index(name = "idx_vehicle_type", columnList = "type")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", nullable = false, unique = true, length = 20)
    @NotBlank(message = "License plate is required")
    @Size(max = 20, message = "License plate must not exceed 20 characters")
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NotNull(message = "Vehicle type is required")
    private VehicleType type;

    @Column(length = 100)
    @Size(max = 100, message = "Brand must not exceed 100 characters")
    private String brand;

    @Column(length = 100)
    @Size(max = 100, message = "Model must not exceed 100 characters")
    private String model;

    @Column
    @Min(value = 1900, message = "Year must be 1900 or later")
    @Max(value = 2100, message = "Year must be 2100 or earlier")
    private Integer year;

    @Column(name = "capacity_kg", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "Capacity in kg must be positive")
    private BigDecimal capacityKg;

    @Column(name = "capacity_passengers")
    @Min(value = 0, message = "Passenger capacity must be positive")
    private Integer capacityPassengers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false, foreignKey = @ForeignKey(name = "fk_vehicle_company"))
    @NotNull(message = "Company is required")
    private Company company;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}