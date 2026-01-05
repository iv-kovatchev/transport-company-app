import { useState } from 'react';
import { useDeleteEmployee } from '../../../api/employees/useDeleteEmployee';
import type { EmployeeResponse } from '../../../types/employee.types';

interface UseDeleteEmployeeDialogProps {
  employee?: EmployeeResponse;
  onClose: () => void;
}

export const useDeleteEmployeeDialog = ({ employee, onClose }: UseDeleteEmployeeDialogProps) => {
  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
    open: false,
    message: '',
    severity: 'success',
  });

  const { mutate: deleteEmployee, isPending } = useDeleteEmployee();

  const handleConfirm = () => {
    if (!employee) return;

    deleteEmployee(employee.id, {
      onSuccess: () => {
        setSnackbar({ open: true, message: 'Employee deleted successfully!', severity: 'success' });
        onClose();
      },
      onError: (error: any) => {
        const message = error?.message || 'Failed to delete employee';
        setSnackbar({ open: true, message, severity: 'error' });
      },
    });
  };

  const handleCloseSnackbar = () => {
    setSnackbar({ ...snackbar, open: false });
  };

  return {
    handleConfirm,
    isPending,
    snackbar,
    handleCloseSnackbar,
  };
};
