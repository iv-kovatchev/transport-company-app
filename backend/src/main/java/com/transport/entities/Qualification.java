package com.transport.entities;

import com.transport.enums.QualificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "qualifications",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_employee_qualification",
                        columnNames = {"employee_id", "qualification_type"}
                )
        },
        indexes = {
                @Index(name = "idx_qualification_employee_id", columnList = "employee_id"),
                @Index(name = "idx_qualification_type", columnList = "qualification_type")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false, foreignKey = @ForeignKey(name = "fk_qualification_employee"))
    @NotNull(message = "Employee is required")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(name = "qualification_type", nullable = false, length = 50)
    @NotNull(message = "Qualification type is required")
    private QualificationType qualificationType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
