package com.transport.dtos.report;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanySummaryReport {
    private Long companyId;
    private String companyName;
    private Long totalTransports;
    private BigDecimal totalRevenue;
    private Long paidTransports;
    private Long unpaidTransports;
    private BigDecimal paidRevenue;
    private BigDecimal unpaidRevenue;
    private Long totalVehicles;
    private Long totalEmployees;
}
