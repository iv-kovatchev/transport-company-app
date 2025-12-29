package com.transport.dtos.qualification;

import com.transport.enums.QualificationType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualificationCreateRequest {
    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Qualification type is required")
    private QualificationType qualificationType;
}