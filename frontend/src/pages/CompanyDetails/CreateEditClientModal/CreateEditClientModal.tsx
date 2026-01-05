import { Controller } from 'react-hook-form';
import { TextField, Box, Snackbar, Alert } from '@mui/material';
import Modal from '../../../components/Modal/Modal';
import { useCreateEditClientModal } from './useCreateEditClientModal';
import type { ClientResponse } from '../../../types/client.types';

interface CreateEditClientModalProps {
  open: boolean;
  onClose: () => void;
  client?: ClientResponse;
  companyId: number;
}

export const CreateEditClientModal = ({ open, onClose, client, companyId }: CreateEditClientModalProps) => {
  const { control, handleSubmit, errors, isPending, snackbar, handleCloseSnackbar, mode } = useCreateEditClientModal({
    open,
    onClose,
    client,
    companyId,
  });

  return (
    <>
      <Modal
        open={open}
        onClose={onClose}
        onSubmit={handleSubmit}
        title={mode === 'edit' ? 'Edit Client' : 'Create Client'}
        submitText={mode === 'edit' ? 'Update' : 'Create'}
        isLoading={isPending}
        maxWidth="sm"
      >
        <Box display="flex" flexDirection="column" gap={2}>
          <Controller
            name="name"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Name *"
                error={!!errors.name}
                helperText={errors.name?.message}
                fullWidth
              />
            )}
          />

          <Controller
            name="phone"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Phone"
                error={!!errors.phone}
                helperText={errors.phone?.message}
                fullWidth
                placeholder="+359888123456"
              />
            )}
          />

          <Controller
            name="email"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Email *"
                type="email"
                error={!!errors.email}
                helperText={errors.email?.message}
                fullWidth
              />
            )}
          />

          <Controller
            name="address"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Address"
                error={!!errors.address}
                helperText={errors.address?.message}
                fullWidth
                multiline
                rows={2}
              />
            )}
          />
        </Box>
      </Modal>

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

export default CreateEditClientModal;
