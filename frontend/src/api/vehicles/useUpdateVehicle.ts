import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { VehicleResponse } from '../../types/vehicle.types';
import type { UpdateVehicleFormData } from '../../validations/vehicles/updateVehicleSchema';

export const useUpdateVehicle = (vehicleId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: UpdateVehicleFormData) => {
      const response = await http.put<VehicleResponse>(`/vehicles/${vehicleId}`, data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-vehicles'] });
      queryClient.invalidateQueries({ queryKey: ['vehicles', vehicleId] });
      queryClient.invalidateQueries({ queryKey: ['company-summary'] });
    },
  });
};