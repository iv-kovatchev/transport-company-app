import { Controller } from 'react-hook-form';
import { TextField, Box, Snackbar, Alert } from '@mui/material';
import Modal from '../../../components/Modal/Modal';
import { useCreateEditModal } from './useCreateEditModal';
import type { CompanyResponse } from '../../../types/company.types';

interface CreateEditCompanyModalProps {
  open: boolean;
  onClose: () => void;
  company?: CompanyResponse;
}

export const CreateEditCompanyModal = ({ open, onClose, company }: CreateEditCompanyModalProps) => {
  const { control, handleSubmit, errors, isPending, snackbar, handleCloseSnackbar, mode } = useCreateEditModal({ 
    open, 
    onClose, 
    company 
  });

  return (
    <>
      <Modal
        open={open}
        onClose={onClose}
        onSubmit={handleSubmit}
        title={mode === 'edit' ? 'Edit Company' : 'Create Company'}
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
            name="registrationNumber"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Registration Number"
                error={!!errors.registrationNumber}
                helperText={errors.registrationNumber?.message}
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
                label="Email"
                type="email"
                error={!!errors.email}
                helperText={errors.email?.message}
                fullWidth
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

export default CreateEditCompanyModal;