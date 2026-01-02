import { useState } from "react";
import { useGetClients } from "../../api/clients/useGetClients";
import { useGetCompanies } from "../../api/companies/useGetCompanies";
import { useGetEmployees } from "../../api/employees/useGetEmployees";
import { useGetTransports } from "../../api/transports/useGetTransports";
import { useGetVehicles } from "../../api/vehicles/useGetVehicles";
import type { Column } from "../../components/Table/Table.types";
import type { CompanyResponse } from "../../types/company.types";
import { Link } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';

const useDashboard = () => {
  const { data: companies, isLoading: companiesLoading, error: companiesError } = useGetCompanies();
  const { data: clients, isLoading: clientsLoading, error: clientsError } = useGetClients();
  const { data: employees, isLoading: employeesLoading, error: employeesError } = useGetEmployees();
  const { data: transports, isLoading: transportsLoading, error: transportsError } = useGetTransports();
  const { data: vehicles, isLoading: vehiclesLoading, error: vehiclesError } = useGetVehicles();

  const isLoading = companiesLoading || clientsLoading || employeesLoading || transportsLoading || vehiclesLoading;
  const error = companiesError || clientsError || employeesError || transportsError || vehiclesError;

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedCompany, setSelectedCompany] = useState<CompanyResponse | undefined>(undefined);

  const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false);
  const [companyToDelete, setCompanyToDelete] = useState<CompanyResponse | undefined>(undefined);

  const handleEdit = (companyId: number) => {
    const company = companies?.find(c => c.id === companyId);
    setSelectedCompany(company);
    setIsModalOpen(true);
  };

  const handleDelete = (companyId: number) => {
    const company = companies?.find(c => c.id === companyId);
    setCompanyToDelete(company);
    setIsDeleteDialogOpen(true);
  };

  const handleCreate = () => {
    setSelectedCompany(undefined);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedCompany(undefined);
  };

  const handleCloseDeleteDialog = () => {
    setIsDeleteDialogOpen(false);
    setCompanyToDelete(undefined);
  };

  const columns: Column<CompanyResponse>[] = [
    { id: 'id', label: 'ID', width: 80 },
    {
      id: 'name',
      label: 'Name',
      width: 200,
      render: (row) => (
        <Link
          component={RouterLink}
          to={`/companies/${row.id}`}
          underline="hover"
          sx={{ color: 'inherit' }}
        >
          {row.name}
        </Link>
      )
    },
    { id: 'registrationNumber', label: 'Registration Number', width: 250 },
    { id: 'address', label: 'Address', width: 350 },
    { id: 'phone', label: 'Phone', width: 250 },
    { id: 'email', label: 'Email', width: 300 },
  ];

  // Calculate stats
  const stats = {
    totalCompanies: companies?.length || 0,
    totalClients: clients?.length || 0,
    totalEmployees: employees?.length || 0,
    totalTransports: transports?.length || 0,
    totalRevenue: transports?.reduce((sum, t) => sum + t.price, 0) || 0,
    pendingPayments: transports?.filter(t => !t.isPaid).length || 0,
    pendingAmount: transports?.filter(t => !t.isPaid).reduce((sum, t) => sum + t.price, 0) || 0,
  };

  // Export transports to CSV
  const handleExportCSV = async () => {
  try {
    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/transports/export/csv`);
    
    if (!response.ok) {
      throw new Error('Failed to export CSV');
    }

    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    
    const timestamp = new Date().toISOString().replace(/[:.]/g, '-').slice(0, -5);
    link.download = `transports_${timestamp}.csv`;
    
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
  } catch (error) {
    console.error('Error exporting CSV:', error);
  }
};

  return {
    companies: companies || [],
    transports: transports || [],
    vehicles: vehicles || [],
    isLoading,
    error,
    handleEdit,
    handleDelete,
    handleCreate,
    columns,
    stats,
    isModalOpen,
    handleCloseModal,
    selectedCompany,
    isDeleteDialogOpen,
    handleCloseDeleteDialog,
    companyToDelete,
    handleExportCSV,
  };
};

export default useDashboard;