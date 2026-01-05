import { useState } from 'react';
import { useDeleteClient } from '../../../api/clients/useDeleteClient';
import type { ClientResponse } from '../../../types/client.types';

interface UseDeleteClientDialogProps {
  client?: ClientResponse;
  onClose: () => void;
}

export const useDeleteClientDialog = ({ client, onClose }: UseDeleteClientDialogProps) => {
  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
    open: false,
    message: '',
    severity: 'success',
  });

  const { mutate: deleteClient, isPending } = useDeleteClient();

  const handleConfirm = () => {
    if (!client) return;

    deleteClient(client.id, {
      onSuccess: () => {
        setSnackbar({ open: true, message: 'Client deleted successfully!', severity: 'success' });
        onClose();
      },
      onError: (error: any) => {
        const message = error?.message || 'Failed to delete client';
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
