import { useState } from 'react';
import { useDeleteVehicle } from '../../../api/vehicles/useDeleteVehicle';
import type { VehicleResponse } from '../../../types/vehicle.types';

interface UseDeleteVehicleDialogProps {
  vehicle?: VehicleResponse;
  onClose: () => void;
}

export const useDeleteVehicleDialog = ({ vehicle, onClose }: UseDeleteVehicleDialogProps) => {
  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
    open: false,
    message: '',
    severity: 'success',
  });

  const { mutate: deleteVehicle, isPending } = useDeleteVehicle();

  const handleConfirm = () => {
    if (!vehicle) return;

    deleteVehicle(vehicle.id, {
      onSuccess: () => {
        setSnackbar({ open: true, message: 'Vehicle deleted successfully!', severity: 'success' });
        onClose();
      },
      onError: (error: any) => {
        const message = error?.message || 'Failed to delete vehicle';
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
