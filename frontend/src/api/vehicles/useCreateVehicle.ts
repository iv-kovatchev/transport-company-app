import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { VehicleResponse } from '../../types/vehicle.types';
import type { CreateVehicleFormData } from '../../validations/vehicles/createVehicleSchema';

export const useCreateVehicle = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: CreateVehicleFormData) => {
      const response = await http.post<VehicleResponse>('/vehicles', data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-vehicles'] });
      queryClient.invalidateQueries({ queryKey: ['company-summary'] });
    },
  });
};
