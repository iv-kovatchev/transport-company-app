import { Controller } from 'react-hook-form';
import { TextField, Box, Snackbar, Alert, MenuItem } from '@mui/material';
import Modal from '../../../components/Modal/Modal';
import { useCreateEditVehicleModal } from './useCreateEditVehicleModal';
import type { VehicleResponse, VehicleType } from '../../../types/vehicle.types';

interface CreateEditVehicleModalProps {
  open: boolean;
  onClose: () => void;
  vehicle?: VehicleResponse;
  companyId: number;
}

const vehicleTypes: VehicleType[] = ['TRUCK', 'BUS', 'VAN', 'CISTERN'];

export const CreateEditVehicleModal = ({ open, onClose, vehicle, companyId }: CreateEditVehicleModalProps) => {
  const { control, handleSubmit, errors, isPending, snackbar, handleCloseSnackbar, mode } = useCreateEditVehicleModal({
    open,
    onClose,
    vehicle,
    companyId,
  });

  return (
    <>
      <Modal
        open={open}
        onClose={onClose}
        onSubmit={handleSubmit}
        title={mode === 'edit' ? 'Edit Vehicle' : 'Create Vehicle'}
        submitText={mode === 'edit' ? 'Update' : 'Create'}
        isLoading={isPending}
        maxWidth="sm"
      >
        <Box display="flex" flexDirection="column" gap={2}>
          <Controller
            name="licensePlate"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="License Plate *"
                error={!!errors.licensePlate}
                helperText={errors.licensePlate?.message}
                fullWidth
              />
            )}
          />

          <Controller
            name="type"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                select
                label="Type *"
                error={!!errors.type}
                helperText={errors.type?.message}
                fullWidth
              >
                {vehicleTypes.map((type) => (
                  <MenuItem key={type} value={type}>
                    {type}
                  </MenuItem>
                ))}
              </TextField>
            )}
          />

          <Controller
            name="brand"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Brand"
                error={!!errors.brand}
                helperText={errors.brand?.message}
                fullWidth
              />
            )}
          />

          <Controller
            name="model"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Model"
                error={!!errors.model}
                helperText={errors.model?.message}
                fullWidth
              />
            )}
          />

          <Controller
            name="year"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Year"
                type="number"
                error={!!errors.year}
                helperText={errors.year?.message}
                fullWidth
                onChange={(e) => field.onChange(e.target.value ? Number(e.target.value) : null)}
                value={field.value || ''}
              />
            )}
          />

          <Controller
            name="capacityKg"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Capacity (kg)"
                type="number"
                error={!!errors.capacityKg}
                helperText={errors.capacityKg?.message}
                fullWidth
                onChange={(e) => field.onChange(e.target.value ? Number(e.target.value) : null)}
                value={field.value || ''}
              />
            )}
          />

          <Controller
            name="capacityPassengers"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Capacity (Passengers)"
                type="number"
                error={!!errors.capacityPassengers}
                helperText={errors.capacityPassengers?.message}
                fullWidth
                onChange={(e) => field.onChange(e.target.value ? Number(e.target.value) : null)}
                value={field.value || ''}
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

export default CreateEditVehicleModal;
