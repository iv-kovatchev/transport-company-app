import Dialog from '../../../components/Dialog/Dialog';
import { Snackbar, Alert } from '@mui/material';
import { useDeleteTransportDialog } from './useDeleteTransportDialog';
import type { TransportResponse } from '../../../types/transport.types';

interface DeleteTransportDialogProps {
  open: boolean;
  onClose: () => void;
  transport?: TransportResponse;
}

export const DeleteTransportDialog = ({ open, onClose, transport }: DeleteTransportDialogProps) => {
  const { handleConfirm, isPending, snackbar, handleCloseSnackbar } = useDeleteTransportDialog({ transport, onClose });

  return (
    <>
      <Dialog
        open={open}
        onClose={onClose}
        onConfirm={handleConfirm}
        title="Delete Transport"
        description={`Are you sure you want to delete transport from "${transport?.startLocation}" to "${transport?.endLocation}"? This action cannot be undone.`}
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

export default DeleteTransportDialog;
