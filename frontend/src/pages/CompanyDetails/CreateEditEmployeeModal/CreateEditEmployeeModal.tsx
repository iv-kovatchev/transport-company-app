import { Controller } from 'react-hook-form';
import { TextField, Box, Snackbar, Alert, FormControlLabel, Checkbox, Typography } from '@mui/material';
import Modal from '../../../components/Modal/Modal';
import { useCreateEditEmployeeModal } from './useCreateEditEmployeeModal';
import type { EmployeeResponse } from '../../../types/employee.types';

interface CreateEditEmployeeModalProps {
  open: boolean;
  onClose: () => void;
  employee?: EmployeeResponse;
  companyId: number;
}

export const CreateEditEmployeeModal = ({ open, onClose, employee, companyId }: CreateEditEmployeeModalProps) => {
  const { 
    control, 
    handleSubmit, 
    errors, 
    isPending, 
    snackbar, 
    handleCloseSnackbar, 
    mode,
    allQualificationTypes,
    selectedQualifications,
    handleQualificationToggle,
  } = useCreateEditEmployeeModal({
    open,
    onClose,
    employee,
    companyId,
  });

  return (
    <>
      <Modal
        open={open}
        onClose={onClose}
        onSubmit={handleSubmit}
        title={mode === 'edit' ? 'Edit Employee' : 'Create Employee'}
        submitText={mode === 'edit' ? 'Update' : 'Create'}
        isLoading={isPending}
        maxWidth="sm"
      >
        <Box display="flex" flexDirection="column" gap={2}>
          <Controller
            name="firstName"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="First Name *"
                error={!!errors.firstName}
                helperText={errors.firstName?.message}
                fullWidth
              />
            )}
          />

          <Controller
            name="lastName"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Last Name *"
                error={!!errors.lastName}
                helperText={errors.lastName?.message}
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
                label="Email"
                type="email"
                error={!!errors.email}
                helperText={errors.email?.message}
                fullWidth
              />
            )}
          />

          <Controller
            name="salary"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Salary *"
                type="number"
                error={!!errors.salary}
                helperText={errors.salary?.message}
                fullWidth
                onChange={(e) => field.onChange(Number(e.target.value))}
              />
            )}
          />

          {/* Qualifications Section */}
          <Box mt={2}>
            <Typography variant="subtitle1" gutterBottom>
              Qualifications
            </Typography>
            <Box display="flex" flexDirection="column">
              {allQualificationTypes.map((qualificationType) => (
                <FormControlLabel
                  key={qualificationType}
                  control={
                    <Checkbox
                      checked={selectedQualifications.includes(qualificationType)}
                      onChange={() => handleQualificationToggle(qualificationType)}
                    />
                  }
                  label={qualificationType.replace(/_/g, ' ')}
                />
              ))}
            </Box>
          </Box>
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

export default CreateEditEmployeeModal;
