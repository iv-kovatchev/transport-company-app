import { Controller } from 'react-hook-form';
import { TextField, Box, Snackbar, Alert, MenuItem } from '@mui/material';
import Modal from '../../../components/Modal/Modal';
import { useCreateEditTransportModal } from './useCreateEditTransportModal';
import type { TransportResponse } from '../../../types/transport.types';
import type { ClientResponse } from '../../../types/client.types';
import type { VehicleResponse } from '../../../types/vehicle.types';
import type { EmployeeResponse } from '../../../types/employee.types';

interface CreateEditTransportModalProps {
  open: boolean;
  onClose: () => void;
  transport?: TransportResponse;
  companyId: number;
  clients: ClientResponse[];
  vehicles: VehicleResponse[];
  employees: EmployeeResponse[];
}

export const CreateEditTransportModal = ({
  open,
  onClose,
  transport,
  companyId,
  clients,
  vehicles,
  employees,
}: CreateEditTransportModalProps) => {
  const {
    control,
    handleSubmit,
    errors,
    isPending,
    snackbar,
    handleCloseSnackbar,
    mode,
    cargoType,
  } = useCreateEditTransportModal({
    open,
    onClose,
    transport,
    companyId,
    clients,
    vehicles,
    employees,
  });

  return (
    <>
      <Modal
        open={open}
        onClose={onClose}
        onSubmit={handleSubmit}
        title={mode === 'edit' ? 'Edit Transport' : 'Create Transport'}
        submitText={mode === 'edit' ? 'Update' : 'Create'}
        isLoading={isPending}
        maxWidth="md"
      >
        <Box display="flex" flexDirection="column" gap={2}>
          {/* Dropdowns - Only in Create mode */}
          {mode === 'create' && (
            <>
              <Controller
                name="clientId"
                control={control}
                render={({ field }) => (
                  <TextField
                    {...field}
                    select
                    label="Client *"
                    error={!!('clientId' in errors ? errors.clientId : undefined)}
                    helperText={'clientId' in errors ? errors.clientId?.message : undefined}
                    fullWidth
                  >
                    <MenuItem value={0} disabled>
                      Select Client
                    </MenuItem>
                    {clients.map((client) => (
                      <MenuItem key={client.id} value={client.id}>
                        {client.name}
                      </MenuItem>
                    ))}
                  </TextField>
                )}
              />

              <Controller
                name="vehicleId"
                control={control}
                render={({ field }) => (
                  <TextField
                    {...field}
                    select
                    label="Vehicle *"
                    error={!!('vehicleId' in errors ? errors.vehicleId : undefined)}
                    helperText={'vehicleId' in errors ? errors.vehicleId?.message : undefined}
                    fullWidth
                  >
                    <MenuItem value={0} disabled>
                      Select Vehicle
                    </MenuItem>
                    {vehicles.map((vehicle) => (
                      <MenuItem key={vehicle.id} value={vehicle.id}>
                        {vehicle.licensePlate} - {vehicle.type}
                      </MenuItem>
                    ))}
                  </TextField>
                )}
              />

              <Controller
                name="driverId"
                control={control}
                render={({ field }) => (
                  <TextField
                    {...field}
                    select
                    label="Driver *"
                    error={!!('driverId' in errors ? errors.driverId : undefined)}
                    helperText={'driverId' in errors ? errors.driverId?.message : undefined}
                    fullWidth
                  >
                    <MenuItem value={0} disabled>
                      Select Driver
                    </MenuItem>
                    {employees.map((employee) => (
                      <MenuItem key={employee.id} value={employee.id}>
                        {employee.firstName} {employee.lastName}
                      </MenuItem>
                    ))}
                  </TextField>
                )}
              />
            </>
          )}

          {/* Cargo Type */}
          <Controller
            name="cargoType"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                select
                label="Cargo Type *"
                error={!!errors.cargoType}
                helperText={errors.cargoType?.message}
                fullWidth
              >
                <MenuItem value="GOODS">GOODS</MenuItem>
                <MenuItem value="PASSENGERS">PASSENGERS</MenuItem>
              </TextField>
            )}
          />

          {/* Conditional Fields based on Cargo Type */}
          {cargoType === 'GOODS' && (
            <>
              <Controller
                name="cargoName"
                control={control}
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="Cargo Name"
                    error={!!errors.cargoName}
                    helperText={errors.cargoName?.message}
                    fullWidth
                  />
                )}
              />

              <Controller
                name="cargoWeightKg"
                control={control}
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="Cargo Weight (kg)"
                    type="number"
                    error={!!errors.cargoWeightKg}
                    helperText={errors.cargoWeightKg?.message}
                    fullWidth
                    onChange={(e) => field.onChange(e.target.value ? Number(e.target.value) : null)}
                    value={field.value || ''}
                  />
                )}
              />
            </>
          )}

          {cargoType === 'PASSENGERS' && (
            <Controller
              name="passengerCount"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Passenger Count *"
                  type="number"
                  error={!!errors.passengerCount}
                  helperText={errors.passengerCount?.message}
                  fullWidth
                  onChange={(e) => field.onChange(e.target.value ? Number(e.target.value) : null)}
                  value={field.value || ''}
                />
              )}
            />
          )}

          {/* Locations */}
          <Controller
            name="startLocation"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Start Location *"
                error={!!errors.startLocation}
                helperText={errors.startLocation?.message}
                fullWidth
              />
            )}
          />

          <Controller
            name="endLocation"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="End Location *"
                error={!!errors.endLocation}
                helperText={errors.endLocation?.message}
                fullWidth
              />
            )}
          />

          {/* Dates */}
          <Controller
            name="departureDate"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Departure Date *"
                type="datetime-local"
                error={!!errors.departureDate}
                helperText={errors.departureDate?.message}
                fullWidth
                InputLabelProps={{ shrink: true }}
              />
            )}
          />

          <Controller
            name="arrivalDate"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Arrival Date"
                type="datetime-local"
                error={!!errors.arrivalDate}
                helperText={errors.arrivalDate?.message}
                fullWidth
                InputLabelProps={{ shrink: true }}
              />
            )}
          />

          {/* Price */}
          <Controller
            name="price"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Price *"
                type="number"
                error={!!errors.price}
                helperText={errors.price?.message}
                fullWidth
                onChange={(e) => field.onChange(Number(e.target.value))}
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

export default CreateEditTransportModal;
