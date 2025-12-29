package com.transport.repositories.Qualification;

import com.transport.entities.Qualification;
import com.transport.enums.QualificationType;
import com.transport.repositories.IRepository;

import java.util.List;
import java.util.Optional;

public interface IQualificationRepository extends IRepository<Qualification, Long> {
    /**
     * Find all qualifications for a specific employee
     */
    List<Qualification> findByEmployeeId(Long employeeId);

    /**
     * Find specific qualification for employee
     */
    Optional<Qualification> findByEmployeeIdAndType(Long employeeId, QualificationType type);

    /**
     * Check if employee already has this qualification type
     */
    boolean existsByEmployeeIdAndType(Long employeeId, QualificationType type);

    /**
     * Delete specific qualification by employee ID and type
     */
    void deleteByEmployeeIdAndType(Long employeeId, QualificationType type);
}
