package com.transport.dtos.report;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRevenueReport {
    private Long employeeId;
    private String employeeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long totalTransports;
    private BigDecimal totalRevenue;
    private BigDecimal averageRevenuePerTransport;
}
