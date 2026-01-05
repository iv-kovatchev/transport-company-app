package com.transport.controllers;

import com.transport.dtos.employee.EmployeeCreateRequest;
import com.transport.dtos.employee.EmployeeResponse;
import com.transport.dtos.employee.EmployeeUpdateRequest;
import com.transport.services.employee.IEmployeeService;
import com.transport.utils.ErrorHandler;
import com.transport.utils.ErrorHandler.ErrorResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public class EmployeeController {
    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * POST /api/employees - Create new employee
     */
    public void create(Context ctx) {
        try {
            EmployeeCreateRequest request = ctx.bodyAsClass(EmployeeCreateRequest.class);
            EmployeeResponse response = employeeService.create(request);
            ctx.status(HttpStatus.CREATED).json(response);
        } catch (ConstraintViolationException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ErrorHandler.handleValidationException(e));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorHandler.ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            if (e.getCause() instanceof IllegalArgumentException cause) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(cause.getMessage()));
            } else {
                ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
            }
        }
    }

    /**
     * GET /api/employees/{id} - Get employee by ID
     */
    public void getById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            EmployeeResponse response = employeeService.getById(id);
            ctx.json(response);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/employees - Get all employees
     */
    public void getAll(Context ctx) {
        try {
            List<EmployeeResponse> responses = employeeService.getAll();
            ctx.json(responses);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * PUT /api/employees/{id} - Update employee
     */
    public void update(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            EmployeeUpdateRequest request = ctx.bodyAsClass(EmployeeUpdateRequest.class);
            EmployeeResponse response = employeeService.update(id, request);
            ctx.json(response);
        } catch (ConstraintViolationException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ErrorHandler.handleValidationException(e));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            if (e.getCause() instanceof IllegalArgumentException cause) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(cause.getMessage()));
            } else {
                e.printStackTrace();
                ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
            }
        }
    }

    /**
     * DELETE /api/employees/{id} - Delete employee
     */
    public void delete(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            employeeService.delete(id);
            ctx.status(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (IllegalStateException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }
}
