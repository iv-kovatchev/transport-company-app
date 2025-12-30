package com.transport.controllers;

import com.transport.dtos.transport.TransportCreateRequest;
import com.transport.dtos.transport.TransportResponse;
import com.transport.dtos.transport.TransportUpdateRequest;
import com.transport.services.transport.ITransportService;
import com.transport.utils.ErrorHandler;
import com.transport.utils.ErrorHandler.ErrorResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public class TransportController {
    private final ITransportService transportService;

    public TransportController(ITransportService transportService) {
        this.transportService = transportService;
    }

    /**
     * POST /api/transports - Create new transport
     */
    public void create(Context ctx) {
        try {
            TransportCreateRequest request = ctx.bodyAsClass(TransportCreateRequest.class);
            TransportResponse response = transportService.create(request);
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
     * GET /api/transports/{id} - Get transport by ID
     */
    public void getById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            TransportResponse response = transportService.getById(id);
            ctx.json(response);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/transports - Get all transports
     */
    public void getAll(Context ctx) {
        try {
            String isPaidParam = ctx.queryParam("isPaid");
            List<TransportResponse> responses;

            if (isPaidParam != null) {
                Boolean isPaid = Boolean.parseBoolean(isPaidParam);
                responses = transportService.getAllByPaymentStatus(isPaid);
            } else {
                responses = transportService.getAll();
            }

            ctx.json(responses);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * PUT /api/transports/{id} - Update transport
     */
    public void update(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            TransportUpdateRequest request = ctx.bodyAsClass(TransportUpdateRequest.class);
            TransportResponse response = transportService.update(id, request);
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
     * DELETE /api/transports/{id} - Delete transport
     */
    public void delete(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            transportService.delete(id);
            ctx.status(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * PUT /api/transports/{id}/mark-paid - Mark transport as paid
     */
    public void markAsPaid(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            TransportResponse response = transportService.markAsPaid(id);
            ctx.json(response);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * GET /api/transports/export/csv - Export all transports to CSV
     */
    public void exportCsv(Context ctx) {
        try {
            // Generate CSV
            byte[] csvData = transportService.exportToCsv();

            // Generate filename with current date
            String timestamp = java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String filename = "transports_" + timestamp + ".csv";

            // Set response headers for file download
            ctx.contentType("text/csv; charset=utf-8");
            ctx.header("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            ctx.result(csvData);

        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(new ErrorResponse("Error exporting transports to CSV: " + e.getMessage()));
        }
    }
}
