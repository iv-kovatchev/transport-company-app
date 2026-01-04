import { useParams, useNavigate } from 'react-router-dom';
import { useGetCompany } from '../../api/companies/useGetCompany';
import { useGetCompanySummary } from '../../api/reports/useGetCompanySummary';
import { useGetDriversPerformance } from '../../api/reports/useGetDriversPerformance';

export const useCompanyDetails = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const companyId = Number(id);

  const { data: company, isLoading: isLoadingCompany } = useGetCompany(companyId);
  const { data: summary, isLoading: isLoadingSummary } = useGetCompanySummary(companyId); 
  const { data: drivers, isLoading: isLoadingDrivers } = useGetDriversPerformance(companyId);

  const isLoading = isLoadingCompany || isLoadingSummary || isLoadingDrivers;

  const handleBackToDashboard = () => {
    navigate('/dashboard');
  };

  return {
    company,
    summary,
    drivers,
    isLoading,
    handleBackToDashboard,
  };
};