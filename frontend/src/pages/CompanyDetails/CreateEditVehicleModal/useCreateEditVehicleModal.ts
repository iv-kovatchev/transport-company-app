import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { createVehicleSchema, type CreateVehicleFormData } from '../../../validations/vehicles/createVehicleSchema';
import { updateVehicleSchema, type UpdateVehicleFormData } from '../../../validations/vehicles/updateVehicleSchema';
import { useCreateVehicle } from '../../../api/vehicles/useCreateVehicle';
import { useUpdateVehicle } from '../../../api/vehicles/useUpdateVehicle';
import type { VehicleResponse } from '../../../types/vehicle.types';

interface UseCreateEditVehicleModalProps {
  open: boolean;
  onClose: () => void;
  vehicle?: VehicleResponse;
  companyId: number;
}

export const useCreateEditVehicleModal = ({ open, onClose, vehicle, companyId }: UseCreateEditVehicleModalProps) => {
  const mode = vehicle ? 'edit' : 'create';

  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
    open: false,
    message: '',
    severity: 'success',
  });

  const { mutate: createVehicle, isPending: isCreating } = useCreateVehicle();
  const { mutate: updateVehicle, isPending: isUpdating } = useUpdateVehicle(vehicle?.id || 0);

  const isPending = isCreating || isUpdating;

  const { control, handleSubmit, reset, formState: { errors } } = useForm<CreateVehicleFormData | UpdateVehicleFormData>({
    resolver: zodResolver(mode === 'edit' ? updateVehicleSchema : createVehicleSchema),
    defaultValues: {
      licensePlate: '',
      type: 'TRUCK',
      brand: '',
      model: '',
      year: null,
      capacityKg: null,
      capacityPassengers: null,
      ...(mode === 'create' && { companyId }),
    },
  });

  // Reset form when modal closes
  useEffect(() => {
    if (!open) {
      reset({
        licensePlate: '',
        type: 'TRUCK',
        brand: '',
        model: '',
        year: null,
        capacityKg: null,
        capacityPassengers: null,
        ...(mode === 'create' && { companyId }),
      });
    }
  }, [open, reset, mode, companyId]);

  // Populate form with vehicle data in edit mode
  useEffect(() => {
    if (mode === 'edit' && vehicle && open) {
      reset({
        licensePlate: vehicle.licensePlate,
        type: vehicle.type,
        brand: vehicle.brand || '',
        model: vehicle.model || '',
        year: vehicle.year,
        capacityKg: vehicle.capacityKg,
        capacityPassengers: vehicle.capacityPassengers,
      });
    }
  }, [mode, vehicle, open, reset]);

  const onSubmit = (data: CreateVehicleFormData | UpdateVehicleFormData) => {
    const cleanedData = Object.fromEntries(
      Object.entries(data).filter(([_, value]) => value !== '' && value !== null)
    ) as CreateVehicleFormData | UpdateVehicleFormData;

    if (mode === 'edit') {
      updateVehicle(cleanedData as UpdateVehicleFormData, {
        onSuccess: () => {
          setSnackbar({ open: true, message: 'Vehicle updated successfully!', severity: 'success' });
          onClose();
          reset();
        },
        onError: (error: any) => {
          const message = error?.message || 'Failed to update vehicle';
          setSnackbar({ open: true, message, severity: 'error' });
        },
      });
    } else {
      createVehicle(cleanedData as CreateVehicleFormData, {
        onSuccess: () => {
          setSnackbar({ open: true, message: 'Vehicle created successfully!', severity: 'success' });
          onClose();
          reset();
        },
        onError: (error: any) => {
          const message = error?.message || 'Failed to create vehicle';
          setSnackbar({ open: true, message, severity: 'error' });
        },
      });
    }
  };

  const handleCloseSnackbar = () => {
    setSnackbar({ ...snackbar, open: false });
  };

  return {
    control,
    handleSubmit: handleSubmit(onSubmit),
    errors,
    isPending,
    snackbar,
    handleCloseSnackbar,
    mode,
  };
};