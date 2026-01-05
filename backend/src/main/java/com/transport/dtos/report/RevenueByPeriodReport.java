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
public class RevenueByPeriodReport {
    private Long companyId;
    private String companyName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private BigDecimal totalRevenue;
    private BigDecimal paidRevenue;
    private BigDecimal unpaidRevenue;
    private Long transportsCount;
}
