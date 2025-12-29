package com.transport.controllers;

import com.transport.dtos.qualification.QualificationCreateRequest;
import com.transport.dtos.qualification.QualificationResponse;
import com.transport.enums.QualificationType;
import com.transport.services.qualification.IQualificationService;
import com.transport.utils.ErrorHandler;
import com.transport.utils.ErrorHandler.ErrorResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public class QualificationController {
    private final IQualificationService qualificationService;

    public QualificationController(IQualificationService qualificationService) {
        this.qualificationService = qualificationService;
    }

    /**
     * POST /api/qualifications - Add qualification to employee
     */
    public void create(Context ctx) {
        try {
            QualificationCreateRequest request = ctx.bodyAsClass(QualificationCreateRequest.class);
            QualificationResponse response = qualificationService.create(request);
            ctx.status(HttpStatus.CREATED).json(response);
        } catch (ConstraintViolationException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ErrorHandler.handleValidationException(e));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            if (e.getCause() instanceof IllegalArgumentException cause) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(cause.getMessage()));
            } else {
                ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
            }
        }
    }

    /**
     * GET /api/employees/{employeeId}/qualifications - Get all qualifications for employee
     */
    public void getByEmployeeId(Context ctx) {
        try {
            Long employeeId = Long.parseLong(ctx.pathParam("employeeId"));
            List<QualificationResponse> responses = qualificationService.getByEmployeeId(employeeId);
            ctx.json(responses);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * DELETE /api/employees/{employeeId}/qualifications/{qualificationType} - Delete specific qualification
     */
    public void deleteByEmployeeIdAndType(Context ctx) {
        try {
            Long employeeId = Long.parseLong(ctx.pathParam("employeeId"));
            String typeString = ctx.pathParam("qualificationType");
            QualificationType type = QualificationType.fromValue(typeString);

            qualificationService.deleteByEmployeeIdAndType(employeeId, type);
            ctx.status(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * DELETE /api/qualifications/{id} - Delete qualification by ID
     */
    public void delete(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            qualificationService.delete(id);
            ctx.status(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }
}
