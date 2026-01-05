import Dialog from '../../../components/Dialog/Dialog';
import { Snackbar, Alert } from '@mui/material';
import { useDeleteClientDialog } from './useDeleteClientDialog';
import type { ClientResponse } from '../../../types/client.types';

interface DeleteClientDialogProps {
  open: boolean;
  onClose: () => void;
  client?: ClientResponse;
}

export const DeleteClientDialog = ({ open, onClose, client }: DeleteClientDialogProps) => {
  const { handleConfirm, isPending, snackbar, handleCloseSnackbar } = useDeleteClientDialog({ client, onClose });

  return (
    <>
      <Dialog
        open={open}
        onClose={onClose}
        onConfirm={handleConfirm}
        title="Delete Client"
        description={`Are you sure you want to delete client "${client?.name}"? This action cannot be undone.`}
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

export default DeleteClientDialog;
