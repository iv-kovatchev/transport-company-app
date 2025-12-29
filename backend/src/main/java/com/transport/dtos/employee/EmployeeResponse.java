package com.transport.dtos.employee;

import com.transport.dtos.qualification.QualificationResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private BigDecimal salary;
    private Long companyId;

    @Builder.Default
    private List<QualificationResponse> qualifications = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
