package com.transport.controllers;

import com.transport.dtos.client.ClientCreateRequest;
import com.transport.dtos.client.ClientResponse;
import com.transport.dtos.client.ClientUpdateRequest;
import com.transport.services.client.IClientService;
import com.transport.utils.ErrorHandler;
import com.transport.utils.ErrorHandler.ErrorResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public class ClientController {
    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * POST /api/clients - Create new client
     */
    public void create(Context ctx) {
        try {
            ClientCreateRequest request = ctx.bodyAsClass(ClientCreateRequest.class);
            ClientResponse response = clientService.create(request);
            ctx.status(HttpStatus.CREATED).json(response);
        } catch (ConstraintViolationException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ErrorHandler.handleValidationException(e));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/clients/{id} - Get client by ID
     */
    public void getById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            ClientResponse response = clientService.getById(id);
            ctx.json(response);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/clients - Get all clients
     */
    public void getAll(Context ctx) {
        try {
            List<ClientResponse> responses = clientService.getAll();
            ctx.json(responses);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * PUT /api/clients/{id} - Update client
     */
    public void update(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            ClientUpdateRequest request = ctx.bodyAsClass(ClientUpdateRequest.class);
            ClientResponse response = clientService.update(id, request);
            ctx.json(response);
        } catch (ConstraintViolationException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ErrorHandler.handleValidationException(e));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * DELETE /api/clients/{id} - Delete client
     */
    public void delete(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            clientService.delete(id);
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
