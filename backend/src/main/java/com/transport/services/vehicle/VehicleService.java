package com.transport.services.vehicle;

import com.transport.dtos.vehicle.VehicleCreateRequest;
import com.transport.dtos.vehicle.VehicleResponse;
import com.transport.dtos.vehicle.VehicleUpdateRequest;
import com.transport.entities.Company;
import com.transport.entities.Vehicle;
import com.transport.repositories.Company.ICompanyRepository;
import com.transport.repositories.Vehicle.IVehicleRepository;
import com.transport.utils.EntityMapper;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleService implements IVehicleService {
    private final IVehicleRepository vehicleRepository;
    private final ICompanyRepository companyRepository;

    public VehicleService(IVehicleRepository vehicleRepository, ICompanyRepository companyRepository) {
        this.vehicleRepository = vehicleRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public VehicleResponse create(VehicleCreateRequest request) {
        // Check if company exists
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + request.getCompanyId()));

        // Check if license plate already exists
        if (vehicleRepository.existsByLicensePlateAndIdNot(request.getLicensePlate(), 0L)) {
            throw new IllegalArgumentException("Vehicle with license plate '" + request.getLicensePlate() + "' already exists");
        }

        // Map DTO to Entity
        Vehicle vehicle = Vehicle.builder()
                .licensePlate(request.getLicensePlate())
                .type(request.getType())
                .brand(request.getBrand())
                .model(request.getModel())
                .year(request.getYear())
                .capacityKg(request.getCapacityKg())
                .capacityPassengers(request.getCapacityPassengers())
                .company(company)
                .build();

        // Save to database
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        // Map Entity to Response DTO
        return EntityMapper.toVehicleResponse(savedVehicle);
    }

    @Override
    public VehicleResponse getById(Long id) {
        Vehicle vehicle = vehicleRepository.findByIdWithCompany(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + id));

        return EntityMapper.toVehicleResponse(vehicle);
    }

    @Override
    public List<VehicleResponse> getAll() {
        return vehicleRepository.findAllWithCompany()
                .stream()
                .map(EntityMapper::toVehicleResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponse update(Long id, VehicleUpdateRequest request) {
        // Check if vehicle exists
        Vehicle existingVehicle = vehicleRepository.findByIdWithCompany(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + id));

        // Check license plate uniqueness (if license plate is being changed)
        if (!existingVehicle.getLicensePlate().equals(request.getLicensePlate())
                && vehicleRepository.existsByLicensePlateAndIdNot(request.getLicensePlate(), id)) {
            throw new IllegalArgumentException("Vehicle with license plate '" + request.getLicensePlate() + "' already exists");
        }

        // Update fields (company CANNOT be changed!)
        existingVehicle.setLicensePlate(request.getLicensePlate());
        existingVehicle.setType(request.getType());
        existingVehicle.setBrand(request.getBrand());
        existingVehicle.setModel(request.getModel());
        existingVehicle.setYear(request.getYear());
        existingVehicle.setCapacityKg(request.getCapacityKg());
        existingVehicle.setCapacityPassengers(request.getCapacityPassengers());

        // Save updated entity
        Vehicle updatedVehicle = vehicleRepository.update(existingVehicle);

        return EntityMapper.toVehicleResponse(updatedVehicle);
    }

    @Override
    public void delete(Long id) {
        // Check if vehicle exists
        if (vehicleRepository.findByIdWithCompany(id).isEmpty()) {
            throw new IllegalArgumentException("Vehicle not found with id: " + id);
        }

        vehicleRepository.delete(id);
    }
}
