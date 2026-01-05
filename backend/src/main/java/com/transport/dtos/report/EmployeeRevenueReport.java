package com.transport.dtos.report;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Long totalTransports;
    private BigDecimal totalRevenue;
    private BigDecimal averageRevenuePerTransport;
}
