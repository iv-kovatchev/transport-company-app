package com.transport.dtos.qualification;

import com.transport.enums.QualificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualificationResponse {
    private Long id;
    private Long employeeId;
    private QualificationType qualificationType;
    private LocalDateTime createdAt;
}