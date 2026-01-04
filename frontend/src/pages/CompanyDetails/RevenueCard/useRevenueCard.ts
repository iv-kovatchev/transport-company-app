import { useState } from 'react';
import { useGetCompanyRevenue } from '../../../api/reports/useGetCompanyRevenue';

interface UseRevenueCardProps {
  companyId: number;
}

export const useRevenueCard = ({ companyId }: UseRevenueCardProps) => {
  const currentYear = new Date().getFullYear();
  
  const [startDate, setStartDate] = useState(`${currentYear}-01-01`);
  const [endDate, setEndDate] = useState(`${currentYear}-12-31`);
  const [appliedStartDate, setAppliedStartDate] = useState(`${currentYear}-01-01`);
  const [appliedEndDate, setAppliedEndDate] = useState(`${currentYear}-12-31`);
  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
    open: false,
    message: '',
    severity: 'success',
  });

  const { data: revenue, isLoading, error } = useGetCompanyRevenue(companyId, appliedStartDate, appliedEndDate);

  const handleApply = () => {
    // Validate dates before applying
    if (new Date(startDate) > new Date(endDate)) {
      setSnackbar({
        open: true,
        message: 'Start date must be before or equal to end date',
        severity: 'error',
      });
      return;
    }

    setAppliedStartDate(startDate);
    setAppliedEndDate(endDate);
  };

  const handleStartDateChange = (date: string) => {
    setStartDate(date);
  };

  const handleEndDateChange = (date: string) => {
    setEndDate(date);
  };

  const handleCloseSnackbar = () => {
    setSnackbar({ ...snackbar, open: false });
  };

  // Handle API errors
  if (error) {
    const errorMessage = (error as any)?.response?.data?.message || 'An error occurred';
    if (snackbar.message !== errorMessage) {
      setSnackbar({
        open: true,
        message: errorMessage,
        severity: 'error',
      });
    }
  }

  return {
    revenue,
    isLoading,
    startDate,
    endDate,
    snackbar,
    handleStartDateChange,
    handleEndDateChange,
    handleApply,
    handleCloseSnackbar,
  };
};