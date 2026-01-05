import { useState } from 'react';
import { Chip, IconButton } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import type { EmployeeResponse } from '../../../types/employee.types';
import type { VehicleResponse } from '../../../types/vehicle.types';
import type { Column } from '../../../components/Table/Table.types';
import type { ClientResponse } from '../../../types/client.types';
import type { TransportResponse } from '../../../types/transport.types';

export const useCompanyDetailsTabs = () => {
  const [activeTab, setActiveTab] = useState(0);

  // Employee modal/dialog states
  const [isEmployeeModalOpen, setIsEmployeeModalOpen] = useState(false);
  const [selectedEmployee, setSelectedEmployee] = useState<EmployeeResponse | undefined>(undefined);
  const [isEmployeeDeleteDialogOpen, setIsEmployeeDeleteDialogOpen] = useState(false);
  const [employeeToDelete, setEmployeeToDelete] = useState<EmployeeResponse | undefined>(undefined);

  // Vehicle modal/dialog states
  const [isVehicleModalOpen, setIsVehicleModalOpen] = useState(false);
  const [selectedVehicle, setSelectedVehicle] = useState<VehicleResponse | undefined>(undefined);
  const [isVehicleDeleteDialogOpen, setIsVehicleDeleteDialogOpen] = useState(false);
  const [vehicleToDelete, setVehicleToDelete] = useState<VehicleResponse | undefined>(undefined);

  const handleTabChange = (_event: React.SyntheticEvent, newValue: number) => {
    setActiveTab(newValue);
  };

  // Employee handlers
  const handleCreateEmployee = () => {
    setSelectedEmployee(undefined);
    setIsEmployeeModalOpen(true);
  };

  const handleEditEmployee = (employeeId: number, employees: EmployeeResponse[]) => {
    const employee = employees.find(e => e.id === employeeId);
    setSelectedEmployee(employee);
    setIsEmployeeModalOpen(true);
  };

  const handleDeleteEmployee = (employeeId: number, employees: EmployeeResponse[]) => {
    const employee = employees.find(e => e.id === employeeId);
    setEmployeeToDelete(employee);
    setIsEmployeeDeleteDialogOpen(true);
  };

  const handleCloseEmployeeModal = () => {
    setIsEmployeeModalOpen(false);
    setSelectedEmployee(undefined);
  };

  const handleCloseEmployeeDeleteDialog = () => {
    setIsEmployeeDeleteDialogOpen(false);
    setEmployeeToDelete(undefined);
  };

  // Vehicle handlers
  const handleCreateVehicle = () => {
    setSelectedVehicle(undefined);
    setIsVehicleModalOpen(true);
  };

  const handleEditVehicle = (vehicleId: number, vehicles: VehicleResponse[]) => {
    const vehicle = vehicles.find(v => v.id === vehicleId);
    setSelectedVehicle(vehicle);
    setIsVehicleModalOpen(true);
  };

  const handleDeleteVehicle = (vehicleId: number, vehicles: VehicleResponse[]) => {
    const vehicle = vehicles.find(v => v.id === vehicleId);
    setVehicleToDelete(vehicle);
    setIsVehicleDeleteDialogOpen(true);
  };

  const handleCloseVehicleModal = () => {
    setIsVehicleModalOpen(false);
    setSelectedVehicle(undefined);
  };

  const handleCloseVehicleDeleteDialog = () => {
    setIsVehicleDeleteDialogOpen(false);
    setVehicleToDelete(undefined);
  };

  // Employee columns
  const employeeColumns: Column<EmployeeResponse>[] = [
    { id: 'id', label: 'ID', width: 80 },
    { id: 'firstName', label: 'First Name', width: 200 },
    { id: 'lastName', label: 'Last Name', width: 200 },
    { id: 'phone', label: 'Phone', width: 200 },
    { id: 'email', label: 'Email', width: 250 },
    { 
      id: 'salary', 
      label: 'Salary', 
      width: 150, 
      render: (row) => `${row.salary.toFixed(2)} BGN` 
    },
  ];

  // Vehicle columns
  const vehicleColumns: Column<VehicleResponse>[] = [
    { id: 'id', label: 'ID', width: 80 },
    { id: 'licensePlate', label: 'License Plate', width: 150 },
    { 
      id: 'type', 
      label: 'Type', 
      width: 120,
      render: (row) => (
        <Chip label={row.type} size="small" color="primary" />
      )
    },
    { id: 'brand', label: 'Brand', width: 150 },
    { id: 'model', label: 'Model', width: 150 },
    { id: 'year', label: 'Year', width: 100 },
    { id: 'capacityKg', label: 'Capacity (kg)', width: 150 },
    { id: 'capacityPassengers', label: 'Passengers', width: 120 },
  ];

  // Client columns
  const clientColumns: Column<ClientResponse>[] = [
    { id: 'id', label: 'ID', width: 80 },
    { id: 'name', label: 'Name', width: 250 },
    { id: 'phone', label: 'Phone', width: 200 },
    { id: 'email', label: 'Email', width: 250 },
    { id: 'address', label: 'Address', width: 350 },
  ];

  // Transport columns
  const transportColumns: Column<TransportResponse>[] = [
    { id: 'id', label: 'ID', width: 80 },
    { 
      id: 'cargoType', 
      label: 'Cargo Type', 
      width: 120,
      render: (row) => (
        <Chip 
          label={row.cargoType} 
          size="small" 
          color={row.cargoType === 'GOODS' ? 'primary' : 'secondary'} 
        />
      )
    },
    { id: 'cargoName', label: 'Cargo Name', width: 200 },
    { id: 'startLocation', label: 'Start Location', width: 200 },
    { id: 'endLocation', label: 'End Location', width: 200 },
    { id: 'departureDate', label: 'Departure Date', width: 150 },
    { 
      id: 'price', 
      label: 'Price', 
      width: 120,
      render: (row) => `${row.price.toFixed(2)} BGN`
    },
    { 
      id: 'isPaid', 
      label: 'Status', 
      width: 100,
      render: (row) => (
        <Chip 
          label={row.isPaid ? 'Paid' : 'Unpaid'} 
          size="small" 
          color={row.isPaid ? 'success' : 'error'} 
        />
      )
    },
  ];

  const getEmployeeActions = (employees: EmployeeResponse[]) => (row: EmployeeResponse) => (
    <>
      <IconButton size="small" onClick={() => handleEditEmployee(row.id, employees)} color="primary">
        <EditIcon />
      </IconButton>
      <IconButton size="small" onClick={() => handleDeleteEmployee(row.id, employees)} color="error">
        <DeleteIcon />
      </IconButton>
    </>
  );

  const getVehicleActions = (vehicles: VehicleResponse[]) => (row: VehicleResponse) => (
    <>
      <IconButton size="small" onClick={() => handleEditVehicle(row.id, vehicles)} color="primary">
        <EditIcon />
      </IconButton>
      <IconButton size="small" onClick={() => handleDeleteVehicle(row.id, vehicles)} color="error">
        <DeleteIcon />
      </IconButton>
    </>
  );

  return {
    activeTab,
    handleTabChange,
    employeeColumns,
    vehicleColumns,
    clientColumns,
    transportColumns,
    // Employee CRUD
    isEmployeeModalOpen,
    selectedEmployee,
    isEmployeeDeleteDialogOpen,
    employeeToDelete,
    handleCreateEmployee,
    handleCloseEmployeeModal,
    handleCloseEmployeeDeleteDialog,
    getEmployeeActions,
    // Vehicle CRUD
    isVehicleModalOpen,
    selectedVehicle,
    isVehicleDeleteDialogOpen,
    vehicleToDelete,
    handleCreateVehicle,
    handleCloseVehicleModal,
    handleCloseVehicleDeleteDialog,
    getVehicleActions,
  };
};
