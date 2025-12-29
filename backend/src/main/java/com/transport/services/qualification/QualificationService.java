package com.transport.services.qualification;

import com.transport.dtos.qualification.QualificationCreateRequest;
import com.transport.dtos.qualification.QualificationResponse;
import com.transport.entities.Employee;
import com.transport.entities.Qualification;
import com.transport.enums.QualificationType;
import com.transport.repositories.Employee.IEmployeeRepository;
import com.transport.repositories.Qualification.IQualificationRepository;
import com.transport.utils.EntityMapper;

import java.util.List;
import java.util.stream.Collectors;

public class QualificationService implements IQualificationService {
    private final IQualificationRepository qualificationRepository;
    private final IEmployeeRepository employeeRepository;

    public QualificationService(IQualificationRepository qualificationRepository,
                                IEmployeeRepository employeeRepository) {
        this.qualificationRepository = qualificationRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public QualificationResponse create(QualificationCreateRequest request) {
        // Check if employee exists
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee not found with id: " + request.getEmployeeId()));

        // Check if employee already has this qualification
        if (qualificationRepository.existsByEmployeeIdAndType(
                request.getEmployeeId(), request.getQualificationType())) {
            throw new IllegalArgumentException(
                    "Employee already has qualification: " + request.getQualificationType());
        }

        // Map DTO to Qualification
        Qualification qualification = Qualification.builder()
                .employee(employee)
                .qualificationType(request.getQualificationType())
                .build();

        // Save to database
        Qualification savedQualification = qualificationRepository.save(qualification);

        // Map to response DTO
        return EntityMapper.toQualificationResponse(savedQualification);
    }

    @Override
    public List<QualificationResponse> getByEmployeeId(Long employeeId) {
        // Check if employee exists
        if (employeeRepository.findById(employeeId).isEmpty()) {
            throw new IllegalArgumentException("Employee not found with id: " + employeeId);
        }

        // Get all qualifications for this employee
        return qualificationRepository.findByEmployeeId(employeeId)
                .stream()
                .map(EntityMapper::toQualificationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByEmployeeIdAndType(Long employeeId, QualificationType type) {
        // Check if employee exists
        if (employeeRepository.findById(employeeId).isEmpty()) {
            throw new IllegalArgumentException("Employee not found with id: " + employeeId);
        }

        // Check if qualification exists
        if (!qualificationRepository.existsByEmployeeIdAndType(employeeId, type)) {
            throw new IllegalArgumentException(
                    "Qualification " + type + " not found for employee " + employeeId);
        }

        // Delete qualification
        qualificationRepository.deleteByEmployeeIdAndType(employeeId, type);
    }

    @Override
    public void delete(Long id) {
        // Check if qualification exists
        if (qualificationRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Qualification not found with id: " + id);
        }

        qualificationRepository.delete(id);
    }
}
