import { useState } from 'react';
import { useDeleteTransport } from '../../../api/transports/useDeleteTransport';
import type { TransportResponse } from '../../../types/transport.types';

interface UseDeleteTransportDialogProps {
  transport?: TransportResponse;
  onClose: () => void;
}

export const useDeleteTransportDialog = ({ transport, onClose }: UseDeleteTransportDialogProps) => {
  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
    open: false,
    message: '',
    severity: 'success',
  });

  const { mutate: deleteTransport, isPending } = useDeleteTransport();

  const handleConfirm = () => {
    if (!transport) return;

    deleteTransport(transport.id, {
      onSuccess: () => {
        setSnackbar({ open: true, message: 'Transport deleted successfully!', severity: 'success' });
        onClose();
      },
      onError: (error: any) => {
        const message = error?.message || 'Failed to delete transport';
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
