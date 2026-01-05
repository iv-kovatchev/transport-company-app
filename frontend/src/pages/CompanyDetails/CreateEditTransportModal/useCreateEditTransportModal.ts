import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { useCreateTransport } from '../../../api/transports/useCreateTransport';
import { useUpdateTransport } from '../../../api/transports/useUpdateTransport';
import type { TransportResponse, CargoType } from '../../../types/transport.types';
import type { ClientResponse } from '../../../types/client.types';
import type { VehicleResponse } from '../../../types/vehicle.types';
import type { EmployeeResponse } from '../../../types/employee.types';
import { createTransportSchema, type CreateTransportFormData } from '../../../validations/tansports/createTransportSchema';
import { updateTransportSchema, type UpdateTransportFormData } from '../../../validations/tansports/updateTransportSchema';

interface UseCreateEditTransportModalProps {
  open: boolean;
  onClose: () => void;
  transport?: TransportResponse;
  companyId: number;
  clients: ClientResponse[];
  vehicles: VehicleResponse[];
  employees: EmployeeResponse[];
}

export const useCreateEditTransportModal = ({
  open,
  onClose,
  transport,
  companyId,
  clients,
  vehicles,
  employees,
}: UseCreateEditTransportModalProps) => {
  const mode = transport ? 'edit' : 'create';

  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
    open: false,
    message: '',
    severity: 'success',
  });

  const { mutate: createTransport, isPending: isCreating } = useCreateTransport();
  const { mutate: updateTransport, isPending: isUpdating } = useUpdateTransport(transport?.id || 0);

  const isPending = isCreating || isUpdating;

  const { control, handleSubmit, reset, watch, formState: { errors } } = useForm<CreateTransportFormData | UpdateTransportFormData>({
    resolver: zodResolver(mode === 'edit' ? updateTransportSchema : createTransportSchema),
    defaultValues: {
      ...(mode === 'create' && {
        companyId,
        clientId: 0,
        vehicleId: 0,
        driverId: 0,
      }),
      cargoType: 'GOODS',
      cargoName: '',
      cargoWeightKg: null,
      passengerCount: null,
      startLocation: '',
      endLocation: '',
      departureDate: '',
      arrivalDate: '',
      price: 0,
    },
  });

  const cargoType = watch('cargoType') as CargoType;

  // Reset form when modal closes
  useEffect(() => {
    if (!open) {
      reset({
        ...(mode === 'create' && {
          companyId,
          clientId: 0,
          vehicleId: 0,
          driverId: 0,
        }),
        cargoType: 'GOODS',
        cargoName: '',
        cargoWeightKg: null,
        passengerCount: null,
        startLocation: '',
        endLocation: '',
        departureDate: '',
        arrivalDate: '',
        price: 0,
      });
    }
  }, [open, reset, mode, companyId]);

  // Populate form with transport data in edit mode
  useEffect(() => {
    if (mode === 'edit' && transport && open) {
      reset({
        cargoType: transport.cargoType,
        cargoName: transport.cargoName || '',
        cargoWeightKg: transport.cargoWeightKg,
        passengerCount: transport.passengerCount,
        startLocation: transport.startLocation,
        endLocation: transport.endLocation,
        departureDate: transport.departureDate,
        arrivalDate: transport.arrivalDate || '',
        price: transport.price,
      });
    }
  }, [mode, transport, open, reset]);

  const onSubmit = (data: CreateTransportFormData | UpdateTransportFormData) => {
    const cleanedData = Object.fromEntries(
      Object.entries(data).filter(([_, value]) => value !== '' && value !== null)
    ) as CreateTransportFormData | UpdateTransportFormData;

    if (mode === 'edit') {
      updateTransport(cleanedData as UpdateTransportFormData, {
        onSuccess: () => {
          setSnackbar({ open: true, message: 'Transport updated successfully!', severity: 'success' });
          onClose();
          reset();
        },
        onError: (error: any) => {
          const message = error?.response?.data?.message || error?.message || 'Failed to update transport';
          setSnackbar({ open: true, message, severity: 'error' });
        },
      });
    } else {
      createTransport(cleanedData as CreateTransportFormData, {
        onSuccess: () => {
          setSnackbar({ open: true, message: 'Transport created successfully!', severity: 'success' });
          onClose();
          reset();
        },
        onError: (error: any) => {
          const message = error?.response?.data?.message || error?.message || 'Failed to create transport';
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
    cargoType,
    clients,
    vehicles,
    employees,
  };
};
