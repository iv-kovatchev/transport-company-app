package com.transport.services.qualification;

import com.transport.dtos.qualification.QualificationCreateRequest;
import com.transport.dtos.qualification.QualificationResponse;
import com.transport.enums.QualificationType;

import java.util.List;

public interface IQualificationService {
    /**
     * Add qualification to employee
     */
    QualificationResponse create(QualificationCreateRequest request);

    /**
     * Get all qualifications for specific employee
     */
    List<QualificationResponse> getByEmployeeId(Long employeeId);

    /**
     * Delete qualification from employee by qualification type
     */
    void deleteByEmployeeIdAndType(Long employeeId, QualificationType type);

    /**
     * Delete qualification by ID
     */
    void delete(Long id);
}
