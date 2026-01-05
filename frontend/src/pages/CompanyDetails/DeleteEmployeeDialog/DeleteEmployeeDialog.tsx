import Dialog from '../../../components/Dialog/Dialog';
import { Snackbar, Alert } from '@mui/material';
import { useDeleteEmployeeDialog } from './useDeleteEmployeeDialog';
import type { EmployeeResponse } from '../../../types/employee.types';

interface DeleteEmployeeDialogProps {
  open: boolean;
  onClose: () => void;
  employee?: EmployeeResponse;
}

export const DeleteEmployeeDialog = ({ open, onClose, employee }: DeleteEmployeeDialogProps) => {
  const { handleConfirm, isPending, snackbar, handleCloseSnackbar } = useDeleteEmployeeDialog({ employee, onClose });

  return (
    <>
      <Dialog
        open={open}
        onClose={onClose}
        onConfirm={handleConfirm}
        title="Delete Employee"
        description={`Are you sure you want to delete "${employee?.firstName} ${employee?.lastName}"? This action cannot be undone.`}
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

export default DeleteEmployeeDialog;
