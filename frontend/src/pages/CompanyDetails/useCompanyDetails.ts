import { useParams, useNavigate } from 'react-router-dom';
import { useGetCompany } from '../../api/companies/useGetCompany';
import { useGetCompanySummary } from '../../api/reports/useGetCompanySummary';
import { useGetDriversPerformance } from '../../api/reports/useGetDriversPerformance';
import { useGetCompanyEmployees } from '../../api/companies/useGetCompanyEmployees';
import { useGetCompanyVehicles } from '../../api/companies/useGetCompanyVehicles';
import { useGetCompanyClients } from '../../api/companies/useGetCompanyClients';
import { useGetCompanyTransports } from '../../api/companies/useGetCompanyTransports';

export const useCompanyDetails = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const companyId = Number(id);

  const { data: company, isLoading: isLoadingCompany } = useGetCompany(companyId);
  const { data: summary, isLoading: isLoadingSummary } = useGetCompanySummary(companyId);
  const { data: drivers, isLoading: isLoadingDrivers } = useGetDriversPerformance(companyId);
  const { data: employees, isLoading: isLoadingEmployees } = useGetCompanyEmployees(companyId);
  const { data: vehicles, isLoading: isLoadingVehicles } = useGetCompanyVehicles(companyId);
  const { data: clients, isLoading: isLoadingClients } = useGetCompanyClients(companyId);
  const { data: transports, isLoading: isLoadingTransports } = useGetCompanyTransports(companyId);

  const isLoading = 
    isLoadingCompany || 
    isLoadingSummary || 
    isLoadingDrivers || 
    isLoadingEmployees || 
    isLoadingVehicles || 
    isLoadingClients || 
    isLoadingTransports;

  const handleBackToDashboard = () => {
    navigate('/dashboard');
  };

  return {
    company,
    summary,
    drivers,
    employees,
    vehicles,
    clients,
    transports,
    isLoading,
    handleBackToDashboard,
  };
};