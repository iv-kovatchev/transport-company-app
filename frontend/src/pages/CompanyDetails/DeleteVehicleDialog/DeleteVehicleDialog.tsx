import Dialog from '../../../components/Dialog/Dialog';
import { Snackbar, Alert } from '@mui/material';
import { useDeleteVehicleDialog } from './useDeleteVehicleDialog';
import type { VehicleResponse } from '../../../types/vehicle.types';

interface DeleteVehicleDialogProps {
  open: boolean;
  onClose: () => void;
  vehicle?: VehicleResponse;
}

export const DeleteVehicleDialog = ({ open, onClose, vehicle }: DeleteVehicleDialogProps) => {
  const { handleConfirm, isPending, snackbar, handleCloseSnackbar } = useDeleteVehicleDialog({ vehicle, onClose });

  return (
    <>
      <Dialog
        open={open}
        onClose={onClose}
        onConfirm={handleConfirm}
        title="Delete Vehicle"
        description={`Are you sure you want to delete vehicle "${vehicle?.licensePlate}"? This action cannot be undone.`}
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

export default DeleteVehicleDialog;
