package com.transport.dtos.report;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverPerformanceReport {
    private Long driverId;
    private String driverName;
    private Long totalTransports;
    private BigDecimal totalRevenue;
    private BigDecimal averageRevenuePerTransport;
}
