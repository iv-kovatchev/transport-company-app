package com.transport.services.transport;

import com.transport.dtos.transport.TransportCreateRequest;
import com.transport.dtos.transport.TransportResponse;
import com.transport.dtos.transport.TransportUpdateRequest;
import com.transport.entities.*;
import com.transport.enums.CargoType;
import com.transport.enums.QualificationType;
import com.transport.enums.VehicleType;
import com.transport.repositories.Client.IClientRepository;
import com.transport.repositories.Company.ICompanyRepository;
import com.transport.repositories.Employee.IEmployeeRepository;
import com.transport.repositories.Transport.ITransportRepository;
import com.transport.repositories.Vehicle.IVehicleRepository;
import com.transport.utils.EntityMapper;
import com.opencsv.CSVWriter;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TransportService implements ITransportService {
    private final ITransportRepository transportRepository;
    private final ICompanyRepository companyRepository;
    private final IClientRepository clientRepository;
    private final IVehicleRepository vehicleRepository;
    private final IEmployeeRepository employeeRepository;

    public TransportService(ITransportRepository transportRepository,
                            ICompanyRepository companyRepository,
                            IClientRepository clientRepository,
                            IVehicleRepository vehicleRepository,
                            IEmployeeRepository employeeRepository) {
        this.transportRepository = transportRepository;
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
        this.vehicleRepository = vehicleRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public TransportResponse create(TransportCreateRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + request.getCompanyId()));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + request.getClientId()));

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + request.getVehicleId()));

        Employee driver = employeeRepository.findByIdWithQualifications(request.getDriverId())
                .orElseThrow(() -> new IllegalArgumentException("Driver not found with id: " + request.getDriverId()));


        if (!client.getCompany().getId().equals(company.getId())) {
            throw new IllegalArgumentException(
                    "Client does not belong to company " + company.getName());
        }

        if (!vehicle.getCompany().getId().equals(company.getId())) {
            throw new IllegalArgumentException(
                    "Vehicle does not belong to company " + company.getName());
        }

        if (!driver.getCompany().getId().equals(company.getId())) {
            throw new IllegalArgumentException(
                    "Driver does not belong to company " + company.getName());
        }

        //Validate cargo type vs vehicle type compatibility
        validateCargoTypeAndVehicle(request.getCargoType(), vehicle, request.getPassengerCount(), request.getCargoWeightKg());

        //Validate driver qualifications
        validateDriverQualifications(driver, vehicle.getType(), request.getCargoType());

        Transport transport = Transport.builder()
                .company(company)
                .client(client)
                .vehicle(vehicle)
                .driver(driver)
                .cargoType(request.getCargoType())
                .cargoName(request.getCargoName())
                .cargoWeightKg(request.getCargoWeightKg())
                .passengerCount(request.getPassengerCount())
                .startLocation(request.getStartLocation())
                .endLocation(request.getEndLocation())
                .departureDate(request.getDepartureDate())
                .arrivalDate(request.getArrivalDate())
                .price(request.getPrice())
                .isPaid(false)
                .build();

        Transport savedTransport = transportRepository.save(transport);

        return EntityMapper.toTransportResponse(savedTransport);
    }

    @Override
    public TransportResponse getById(Long id) {
        Transport transport = transportRepository.findByIdWithRelationships(id)
                .orElseThrow(() -> new IllegalArgumentException("Transport not found with id: " + id));

        return EntityMapper.toTransportResponse(transport);
    }

    @Override
    public List<TransportResponse> getAll() {
        return transportRepository.findAllWithRelationships()
                .stream()
                .map(EntityMapper::toTransportResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransportResponse> getAllByPaymentStatus(Boolean isPaid) {
        return transportRepository.findAllByPaymentStatus(isPaid)
                .stream()
                .map(EntityMapper::toTransportResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TransportResponse update(Long id, TransportUpdateRequest request) {
        // Get existing transport with all relationships
        Transport existingTransport = transportRepository.findByIdWithRelationships(id)
                .orElseThrow(() -> new IllegalArgumentException("Transport not found with id: " + id));

        // Validate cargo type vs vehicle (vehicle cannot be changed, so use existing)
        validateCargoTypeAndVehicle(
                request.getCargoType(),
                existingTransport.getVehicle(),
                request.getPassengerCount(),
                request.getCargoWeightKg()
        );

        // Validate driver qualifications (driver cannot be changed, so use existing)
        validateDriverQualifications(
                existingTransport.getDriver(),
                existingTransport.getVehicle().getType(),
                request.getCargoType()
        );

        // Update fields (company, client, vehicle, driver CANNOT be changed!)
        existingTransport.setCargoType(request.getCargoType());
        existingTransport.setCargoName(request.getCargoName());
        existingTransport.setCargoWeightKg(request.getCargoWeightKg());
        existingTransport.setPassengerCount(request.getPassengerCount());
        existingTransport.setStartLocation(request.getStartLocation());
        existingTransport.setEndLocation(request.getEndLocation());
        existingTransport.setDepartureDate(request.getDepartureDate());
        existingTransport.setArrivalDate(request.getArrivalDate());
        existingTransport.setPrice(request.getPrice());

        // Save updated transport
        Transport updatedTransport = transportRepository.update(existingTransport);

        return EntityMapper.toTransportResponse(updatedTransport);
    }

    @Override
    public void delete(Long id) {
        // Check if transport exists
        if (transportRepository.findByIdWithRelationships(id).isEmpty()) {
            throw new IllegalArgumentException("Transport not found with id: " + id);
        }

        transportRepository.delete(id);
    }

    @Override
    public TransportResponse markAsPaid(Long id) {
        Transport transport = transportRepository.findByIdWithRelationships(id)
                .orElseThrow(() -> new IllegalArgumentException("Transport not found with id: " + id));

        if (transport.getIsPaid()) {
            throw new IllegalArgumentException("Transport is already marked as paid");
        }

        transport.setIsPaid(true);
        transport.setPaymentDate(LocalDateTime.now());

        Transport updatedTransport = transportRepository.update(transport);

        return EntityMapper.toTransportResponse(updatedTransport);
    }

    @Override
    public byte[] exportToCsv() {
        try {
            // Get all transports with relationships
            List<Transport> transports = transportRepository.findAllForExport();

            // Create CSV writer
            StringWriter stringWriter = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(stringWriter);

            // Define date formatter
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Write CSV header
            String[] header = {
                    "Transport ID",
                    "Company Name",
                    "Client Name",
                    "Vehicle Registration",
                    "Vehicle Type",
                    "Driver Name",
                    "Cargo Type",
                    "Cargo Name",
                    "Cargo Weight (kg)",
                    "Passenger Count",
                    "Start Location",
                    "End Location",
                    "Departure Date",
                    "Arrival Date",
                    "Price",
                    "Is Paid",
                    "Payment Date",
                    "Created At"
            };
            csvWriter.writeNext(header);

            // Write data rows
            for (Transport transport : transports) {
                String[] row = {
                        transport.getId().toString(),
                        transport.getCompany().getName(),
                        transport.getClient().getName(),
                        transport.getVehicle().getLicensePlate(),
                        transport.getVehicle().getType().toString(),
                        transport.getDriver().getFirstName() + " " + transport.getDriver().getLastName(),
                        transport.getCargoType().toString(),
                        transport.getCargoName() != null ? transport.getCargoName() : "",
                        transport.getCargoWeightKg() != null ? transport.getCargoWeightKg().toString() : "",
                        transport.getPassengerCount() != null ? transport.getPassengerCount().toString() : "",
                        transport.getStartLocation(),
                        transport.getEndLocation(),
                        transport.getDepartureDate().format(dateFormatter),
                        transport.getArrivalDate() != null ? transport.getArrivalDate().format(dateFormatter) : "",
                        transport.getPrice().toString(),
                        transport.getIsPaid().toString(),
                        transport.getPaymentDate() != null ? transport.getPaymentDate().format(dateFormatter) : "",
                        transport.getCreatedAt().format(dateFormatter)
                };
                csvWriter.writeNext(row);
            }

            csvWriter.close();

            // Convert to bytes
            return stringWriter.toString().getBytes(StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Error exporting transports to CSV", e);
        }
    }

    /**
     * Validate cargo type compatibility with vehicle type and capacity
     */
    private void validateCargoTypeAndVehicle(CargoType cargoType, Vehicle vehicle,
                                             Integer passengerCount, java.math.BigDecimal cargoWeightKg) {
        VehicleType vehicleType = vehicle.getType();

        // PASSENGERS cargo validation
        if (cargoType == CargoType.PASSENGERS) {
            // Only BUS and VAN can transport passengers
            if (vehicleType != VehicleType.BUS && vehicleType != VehicleType.VAN && vehicleType != VehicleType.CAR) {
                throw new IllegalArgumentException(
                        "Vehicle type " + vehicleType + " cannot transport passengers. Use BUS, VAN, or CAR.");
            }

            // Passenger count is required
            if (passengerCount == null || passengerCount == 0) {
                throw new IllegalArgumentException("Passenger count is required for passenger transport");
            }

            // Check vehicle capacity
            if (vehicle.getCapacityPassengers() == null) {
                throw new IllegalArgumentException(
                        "Vehicle does not have passenger capacity defined");
            }

            if (passengerCount > vehicle.getCapacityPassengers()) {
                throw new IllegalArgumentException(
                        "Passenger count (" + passengerCount + ") exceeds vehicle capacity (" +
                                vehicle.getCapacityPassengers() + ")");
            }
        }

        // GOODS cargo validation
        if (cargoType == CargoType.GOODS) {
            // Cargo weight should be provided for goods
            if (cargoWeightKg != null && cargoWeightKg.compareTo(java.math.BigDecimal.ZERO) > 0) {
                // Check vehicle weight capacity
                if (vehicle.getCapacityKg() == null) {
                    throw new IllegalArgumentException(
                            "Vehicle does not have weight capacity defined");
                }

                if (cargoWeightKg.compareTo(vehicle.getCapacityKg()) > 0) {
                    throw new IllegalArgumentException(
                            "Cargo weight (" + cargoWeightKg + " kg) exceeds vehicle capacity (" +
                                    vehicle.getCapacityKg() + " kg)");
                }
            }
        }
    }

    /**
     * Validate driver has required qualifications for vehicle type and cargo type
     */
    private void validateDriverQualifications(Employee driver, VehicleType vehicleType, CargoType cargoType) {
        Set<QualificationType> driverQualifications = driver.getQualifications()
                .stream()
                .map(Qualification::getQualificationType)
                .collect(Collectors.toSet());

        // BUS requires PASSENGER_TRANSPORT_12_PLUS qualification
        if (vehicleType == VehicleType.BUS && cargoType == CargoType.PASSENGERS) {
            if (!driverQualifications.contains(QualificationType.PASSENGER_TRANSPORT_12_PLUS)) {
                throw new IllegalArgumentException(
                        "Driver must have PASSENGER_TRANSPORT_12_PLUS qualification to drive a bus with passengers");
            }
        }

        // TANKER requires TANKER_TRANSPORT qualification
        if (vehicleType == VehicleType.TANKER) {
            if (!driverQualifications.contains(QualificationType.TANKER_TRANSPORT)) {
                throw new IllegalArgumentException(
                        "Driver must have TANKER_TRANSPORT qualification to drive a tanker");
            }
        }

        // TRUCK with heavy cargo requires HEAVY_CARGO qualification
        if (vehicleType == VehicleType.TRUCK && cargoType == CargoType.GOODS) {
            if (!driverQualifications.contains(QualificationType.HEAVY_CARGO)) {
                throw new IllegalArgumentException(
                        "Driver must have HEAVY_CARGO qualification to drive a truck with goods");
            }
        }

        // TRUCK long distance transport requires LONG_DISTANCE qualification
        if (vehicleType == VehicleType.TRUCK) {
            if (!driverQualifications.contains(QualificationType.LONG_DISTANCE)) {
                throw new IllegalArgumentException(
                        "Driver must have LONG_DISTANCE qualification to drive a truck");
            }
        }

        // Any cross-border transport requires INTERNATIONAL qualification
        if (vehicleType == VehicleType.TANKER || vehicleType == VehicleType.TRUCK) {
            if (!driverQualifications.contains(QualificationType.INTERNATIONAL)) {
                throw new IllegalArgumentException(
                        "Driver must have INTERNATIONAL qualification for " + vehicleType + " transport");
            }
        }
    }
}
