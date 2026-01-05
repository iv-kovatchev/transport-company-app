import { useState } from 'react';
import { Chip, IconButton } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import PaymentIcon from '@mui/icons-material/Payment';
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

  // Client modal/dialog states
  const [isClientModalOpen, setIsClientModalOpen] = useState(false);
  const [selectedClient, setSelectedClient] = useState<ClientResponse | undefined>(undefined);
  const [isClientDeleteDialogOpen, setIsClientDeleteDialogOpen] = useState(false);
  const [clientToDelete, setClientToDelete] = useState<ClientResponse | undefined>(undefined);

  // Transport modal/dialog states
  const [isTransportModalOpen, setIsTransportModalOpen] = useState(false);
  const [selectedTransport, setSelectedTransport] = useState<TransportResponse | undefined>(undefined);
  const [isTransportDeleteDialogOpen, setIsTransportDeleteDialogOpen] = useState(false);
  const [transportToDelete, setTransportToDelete] = useState<TransportResponse | undefined>(undefined);
  const [isPayTransportDialogOpen, setIsPayTransportDialogOpen] = useState(false);
  const [transportToPay, setTransportToPay] = useState<TransportResponse | undefined>(undefined);

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

  // Client handlers
  const handleCreateClient = () => {
    setSelectedClient(undefined);
    setIsClientModalOpen(true);
  };

  const handleEditClient = (clientId: number, clients: ClientResponse[]) => {
    const client = clients.find(c => c.id === clientId);
    setSelectedClient(client);
    setIsClientModalOpen(true);
  };

  const handleDeleteClient = (clientId: number, clients: ClientResponse[]) => {
    const client = clients.find(c => c.id === clientId);
    setClientToDelete(client);
    setIsClientDeleteDialogOpen(true);
  };

  const handleCloseClientModal = () => {
    setIsClientModalOpen(false);
    setSelectedClient(undefined);
  };

  const handleCloseClientDeleteDialog = () => {
    setIsClientDeleteDialogOpen(false);
    setClientToDelete(undefined);
  };

  // Transport handlers
  const handleCreateTransport = () => {
    setSelectedTransport(undefined);
    setIsTransportModalOpen(true);
  };

  const handleEditTransport = (transportId: number, transports: TransportResponse[]) => {
    const transport = transports.find(t => t.id === transportId);
    setSelectedTransport(transport);
    setIsTransportModalOpen(true);
  };

  const handleDeleteTransport = (transportId: number, transports: TransportResponse[]) => {
    const transport = transports.find(t => t.id === transportId);
    setTransportToDelete(transport);
    setIsTransportDeleteDialogOpen(true);
  };

  const handlePayTransport = (transportId: number, transports: TransportResponse[]) => {
    const transport = transports.find(t => t.id === transportId);
    setTransportToPay(transport);
    setIsPayTransportDialogOpen(true);
  };

  const handleCloseTransportModal = () => {
    setIsTransportModalOpen(false);
    setSelectedTransport(undefined);
  };

  const handleCloseTransportDeleteDialog = () => {
    setIsTransportDeleteDialogOpen(false);
    setTransportToDelete(undefined);
  };

  const handleClosePayTransportDialog = () => {
    setIsPayTransportDialogOpen(false);
    setTransportToPay(undefined);
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

  const getClientActions = (clients: ClientResponse[]) => (row: ClientResponse) => (
    <>
      <IconButton size="small" onClick={() => handleEditClient(row.id, clients)} color="primary">
        <EditIcon />
      </IconButton>
      <IconButton size="small" onClick={() => handleDeleteClient(row.id, clients)} color="error">
        <DeleteIcon />
      </IconButton>
    </>
  );

  const getTransportActions = (transports: TransportResponse[]) => (row: TransportResponse) => (
    <>
      {!row.isPaid && (
        <IconButton size="small" onClick={() => handlePayTransport(row.id, transports)} color="success">
          <PaymentIcon />
        </IconButton>
      )}
      <IconButton size="small" onClick={() => handleEditTransport(row.id, transports)} color="primary">
        <EditIcon />
      </IconButton>
      <IconButton size="small" onClick={() => handleDeleteTransport(row.id, transports)} color="error">
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
    // Client CRUD
    isClientModalOpen,
    selectedClient,
    isClientDeleteDialogOpen,
    clientToDelete,
    handleCreateClient,
    handleCloseClientModal,
    handleCloseClientDeleteDialog,
    getClientActions,
    // Transport CRUD
    isTransportModalOpen,
    selectedTransport,
    isTransportDeleteDialogOpen,
    transportToDelete,
    isPayTransportDialogOpen,
    transportToPay,
    handleCreateTransport,
    handleCloseTransportModal,
    handleCloseTransportDeleteDialog,
    handleClosePayTransportDialog,
    getTransportActions,
  };
};
