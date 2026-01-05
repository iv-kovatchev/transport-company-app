package com.transport.controllers;

import com.transport.dtos.client.ClientResponse;
import com.transport.dtos.company.CompanyCreateRequest;
import com.transport.dtos.company.CompanyResponse;
import com.transport.dtos.company.CompanyUpdateRequest;
import com.transport.dtos.employee.EmployeeResponse;
import com.transport.dtos.transport.TransportResponse;
import com.transport.dtos.vehicle.VehicleResponse;
import com.transport.services.company.ICompanyService;
import com.transport.utils.ErrorHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

/**
 * REST controller for Company endpoints
 */
public class CompanyController {
    private final ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * POST /api/companies - Create new company
     */
    public void create(Context ctx) {
        try {
            CompanyCreateRequest request = ctx.bodyAsClass(CompanyCreateRequest.class);
            CompanyResponse response = companyService.create(request);
            ctx.status(HttpStatus.CREATED).json(response);
        } catch (ConstraintViolationException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ErrorHandler.handleValidationException(e));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorHandler.ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorHandler.ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/companies/{id} - Get company by ID
     */
    public void getById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            CompanyResponse response = companyService.getById(id);
            ctx.json(response);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/companies - Get all companies
     */
    public void getAll(Context ctx) {
        try {
            List<CompanyResponse> responses = companyService.getAll();
            ctx.json(responses);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * PUT /api/companies/{id} - Update company
     */
    public void update(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            CompanyUpdateRequest request = ctx.bodyAsClass(CompanyUpdateRequest.class);
            CompanyResponse response = companyService.update(id, request);
            ctx.json(response);
        } catch (ConstraintViolationException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ErrorHandler.handleValidationException(e));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorHandler.ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorHandler.ErrorResponse("Internal server error"));
        }
    }

    /**
     * DELETE /api/companies/{id} - Delete company
     */
    public void delete(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            companyService.delete(id);
            ctx.status(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/companies/{id}/employees
     */
    public void getCompanyEmployees(Context ctx) {
        try {
            Long companyId = Long.parseLong(ctx.pathParam("id"));
            List<EmployeeResponse> employees = companyService.getCompanyEmployees(companyId);
            ctx.json(employees);
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Invalid company ID format"));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/companies/{id}/clients
     */
    public void getCompanyClients(Context ctx) {
        try {
            Long companyId = Long.parseLong(ctx.pathParam("id"));
            List<ClientResponse> clients = companyService.getCompanyClients(companyId);
            ctx.json(clients);
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Invalid company ID format"));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/companies/{id}/vehicles
     */
    public void getCompanyVehicles(Context ctx) {
        try {
            Long companyId = Long.parseLong(ctx.pathParam("id"));
            List<VehicleResponse> vehicles = companyService.getCompanyVehicles(companyId);
            ctx.json(vehicles);
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Invalid company ID format"));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/companies/{id}/transports
     */
    public void getCompanyTransports(Context ctx) {
        try {
            Long companyId = Long.parseLong(ctx.pathParam("id"));
            List<TransportResponse> transports = companyService.getCompanyTransports(companyId);
            ctx.json(transports);
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Invalid company ID format"));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * Simple error response DTO
     */
    private record ErrorResponse(String message) {}
}
