import { useState, useEffect } from 'react';
import type { EmployeeResponse } from '../../../types/employee.types';
import { useGetEmployeeRevenue } from '../../../api/reports/useGetEmployeeRevenue';

interface UseEmployeeRevenueCardProps {
  employees: EmployeeResponse[];
}

export const useEmployeeRevenueCard = ({ employees }: UseEmployeeRevenueCardProps) => {
  const currentYear = new Date().getFullYear();
  
  const [selectedEmployeeId, setSelectedEmployeeId] = useState<number | null>(null);
  const [startDate, setStartDate] = useState(`${currentYear}-01-01`);
  const [endDate, setEndDate] = useState(`${currentYear}-12-31`);
  const [appliedStartDate, setAppliedStartDate] = useState(`${currentYear}-01-01`);
  const [appliedEndDate, setAppliedEndDate] = useState(`${currentYear}-12-31`);
  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
    open: false,
    message: '',
    severity: 'success',
  });

  // Set first employee as default when data loads
  useEffect(() => {
    if (employees && employees.length > 0 && selectedEmployeeId === null) {
      setSelectedEmployeeId(employees[0].id);
    }
  }, [employees, selectedEmployeeId]);

  const { data: employeeRevenue, isLoading } = useGetEmployeeRevenue(
    selectedEmployeeId || 0,
    appliedStartDate,
    appliedEndDate
  );

  const selectedEmployee = employees?.find(e => e.id === selectedEmployeeId) || null;

  const handleEmployeeChange = (employeeId: number) => {
    setSelectedEmployeeId(employeeId);
  };

  const handleStartDateChange = (date: string) => {
    setStartDate(date);
  };

  const handleEndDateChange = (date: string) => {
    setEndDate(date);
  };

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

  const handleCloseSnackbar = () => {
    setSnackbar({ ...snackbar, open: false });
  };

  return {
    employeeRevenue,
    isLoading,
    selectedEmployee,
    startDate,
    endDate,
    snackbar,
    handleEmployeeChange,
    handleStartDateChange,
    handleEndDateChange,
    handleApply,
    handleCloseSnackbar,
  };
};