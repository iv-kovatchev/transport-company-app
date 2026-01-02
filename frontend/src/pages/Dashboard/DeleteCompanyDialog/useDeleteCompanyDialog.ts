import { useState } from 'react';
import { useDeleteCompany } from '../../../api/companies/useDeleteCompany';
import type { CompanyResponse } from '../../../types/company.types';

interface UseDeleteCompanyDialogProps {
  company?: CompanyResponse;
  onClose: () => void;
}

export const useDeleteCompanyDialog = ({ company, onClose }: UseDeleteCompanyDialogProps) => {
  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
    open: false,
    message: '',
    severity: 'success',
  });

  const { mutate: deleteCompany, isPending } = useDeleteCompany();

  const handleConfirm = () => {
    if (!company) return;

    deleteCompany(company.id, {
      onSuccess: () => {
        setSnackbar({ open: true, message: 'Company deleted successfully!', severity: 'success' });
        onClose();
      },
      onError: (error: any) => {
        const message = error?.message || 'Failed to delete company';
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