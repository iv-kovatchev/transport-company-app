package com.transport.entities;

import com.transport.enums.CargoType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "transports",
        indexes = {
                @Index(name = "idx_transport_company_id", columnList = "company_id"),
                @Index(name = "idx_transport_client_id", columnList = "client_id"),
                @Index(name = "idx_transport_vehicle_id", columnList = "vehicle_id"),
                @Index(name = "idx_transport_driver_id", columnList = "driver_id"),
                @Index(name = "idx_transport_departure_date", columnList = "departure_date"),
                @Index(name = "idx_transport_is_paid", columnList = "is_paid"),
                @Index(name = "idx_transport_cargo_type", columnList = "cargo_type")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false, foreignKey = @ForeignKey(name = "fk_transport_company"))
    @NotNull(message = "Company is required")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, foreignKey = @ForeignKey(name = "fk_transport_client"))
    @NotNull(message = "Client is required")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false, foreignKey = @ForeignKey(name = "fk_transport_vehicle"))
    @NotNull(message = "Vehicle is required")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false, foreignKey = @ForeignKey(name = "fk_transport_driver"))
    @NotNull(message = "Driver is required")
    private Employee driver;

    @Enumerated(EnumType.STRING)
    @Column(name = "cargo_type", nullable = false, length = 20)
    @NotNull(message = "Cargo type is required")
    private CargoType cargoType;

    @Column(name = "cargo_name", length = 255)
    @Size(max = 255, message = "Cargo name must not exceed 255 characters")
    private String cargoName;

    @Column(name = "cargo_weight_kg", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "Cargo weight must be positive")
    private BigDecimal cargoWeightKg;

    @Column(name = "passenger_count")
    @Min(value = 0, message = "Passenger count must be positive")
    private Integer passengerCount;

    @Column(name = "start_location", nullable = false, length = 255)
    @NotBlank(message = "Start location is required")
    @Size(max = 255, message = "Start location must not exceed 255 characters")
    private String startLocation;

    @Column(name = "end_location", nullable = false, length = 255)
    @NotBlank(message = "End location is required")
    @Size(max = 255, message = "End location must not exceed 255 characters")
    private String endLocation;

    @Column(name = "departure_date", nullable = false)
    @NotNull(message = "Departure date is required")
    private LocalDateTime departureDate;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be positive")
    private BigDecimal price;

    @Column(name = "is_paid", nullable = false)
    @Builder.Default
    private Boolean isPaid = false;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

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