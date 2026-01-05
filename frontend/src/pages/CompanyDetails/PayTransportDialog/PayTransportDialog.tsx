import Dialog from '../../../components/Dialog/Dialog';
import { Snackbar, Alert } from '@mui/material';
import { usePayTransportDialog } from './usePayTransportDialog';
import type { TransportResponse } from '../../../types/transport.types';

interface PayTransportDialogProps {
  open: boolean;
  onClose: () => void;
  transport?: TransportResponse;
}

export const PayTransportDialog = ({ open, onClose, transport }: PayTransportDialogProps) => {
  const { handleConfirm, isPending, snackbar, handleCloseSnackbar } = usePayTransportDialog({ transport, onClose });

  return (
    <>
      <Dialog
        open={open}
        onClose={onClose}
        onConfirm={handleConfirm}
        title="Mark Transport as Paid"
        description={`Are you sure you want to mark this transport as paid? Amount: ${transport?.price.toFixed(2)} BGN`}
        confirmText="Mark as Paid"
        cancelText="Cancel"
        isLoading={isPending}
        severity="info"
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

export default PayTransportDialog;
