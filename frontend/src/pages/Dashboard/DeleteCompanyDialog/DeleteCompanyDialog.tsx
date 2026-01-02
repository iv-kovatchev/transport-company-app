import Dialog from '../../../components/Dialog/Dialog';
import { Snackbar, Alert } from '@mui/material';
import { useDeleteCompanyDialog } from './useDeleteCompanyDialog';
import type { CompanyResponse } from '../../../types/company.types';

interface DeleteCompanyDialogProps {
  open: boolean;
  onClose: () => void;
  company?: CompanyResponse;
}

export const DeleteCompanyDialog = ({ open, onClose, company }: DeleteCompanyDialogProps) => {
  const { handleConfirm, isPending, snackbar, handleCloseSnackbar } = useDeleteCompanyDialog({ company, onClose });

  return (
    <>
      <Dialog
        open={open}
        onClose={onClose}
        onConfirm={handleConfirm}
        title="Delete Company"
        description={`Are you sure you want to delete "${company?.name}"? This action cannot be undone.`}
        confirmText="Delete"
        cancelText="Cancel"
        isLoading={isPending}
        severity="error"
      />

      <Snackbar
        open={snackbar.open}
        autoHideDuration={4000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'left' }}
      >
        <Alert onClose={handleCloseSnackbar} severity={snackbar.severity} sx={{ width: '100%' }}>
          {snackbar.message}
        </Alert>
      </Snackbar>
    </>
  );
};

export default DeleteCompanyDialog;