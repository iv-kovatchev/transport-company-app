package com.transport.controllers;

import com.transport.dtos.company.CompanyCreateRequest;
import com.transport.dtos.company.CompanyResponse;
import com.transport.dtos.company.CompanyUpdateRequest;
import com.transport.services.company.ICompanyService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

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
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
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
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
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
     * Simple error response DTO
     */
    private record ErrorResponse(String message) {}
}
