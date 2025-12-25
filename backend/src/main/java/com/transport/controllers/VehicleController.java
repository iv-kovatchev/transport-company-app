package com.transport.controllers;

import com.transport.dtos.vehicle.VehicleCreateRequest;
import com.transport.dtos.vehicle.VehicleResponse;
import com.transport.dtos.vehicle.VehicleUpdateRequest;
import com.transport.services.vehicle.IVehicleService;
import com.transport.utils.ErrorHandler;
import com.transport.utils.ErrorHandler.ErrorResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public class VehicleController {
    private final IVehicleService vehicleService;

    public VehicleController(IVehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * POST /api/vehicles - Create new vehicle
     */
    public void create(Context ctx) {
        try {
            VehicleCreateRequest request = ctx.bodyAsClass(VehicleCreateRequest.class);
            VehicleResponse response = vehicleService.create(request);
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
     * GET /api/vehicles/{id} - Get vehicle by ID
     */
    public void getById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            VehicleResponse response = vehicleService.getById(id);
            ctx.json(response);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/vehicles - Get all vehicles
     */
    public void getAll(Context ctx) {
        try {
            List<VehicleResponse> responses = vehicleService.getAll();
            ctx.json(responses);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * PUT /api/vehicles/{id} - Update vehicle
     */
    public void update(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            VehicleUpdateRequest request = ctx.bodyAsClass(VehicleUpdateRequest.class);
            VehicleResponse response = vehicleService.update(id, request);
            ctx.json(response);
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
     * DELETE /api/vehicles/{id} - Delete vehicle
     */
    public void delete(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            vehicleService.delete(id);
            ctx.status(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }
}
