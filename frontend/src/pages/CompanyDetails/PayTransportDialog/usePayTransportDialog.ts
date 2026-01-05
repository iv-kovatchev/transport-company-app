import { useState } from 'react';
import { useMarkTransportPaid } from '../../../api/transports/useMarkTransportPaid';
import type { TransportResponse } from '../../../types/transport.types';

interface UsePayTransportDialogProps {
  transport?: TransportResponse;
  onClose: () => void;
}

export const usePayTransportDialog = ({ transport, onClose }: UsePayTransportDialogProps) => {
  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
    open: false,
    message: '',
    severity: 'success',
  });

  const { mutate: markPaid, isPending } = useMarkTransportPaid();

  const handleConfirm = () => {
    if (!transport) return;

    markPaid(transport.id, {
      onSuccess: () => {
        setSnackbar({ open: true, message: 'Transport marked as paid successfully!', severity: 'success' });
        onClose();
      },
      onError: (error: any) => {
        const message = error?.response?.data?.message || error?.message || 'Failed to mark transport as paid';
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
