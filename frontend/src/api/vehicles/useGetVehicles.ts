import { useQuery } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { VehicleResponse } from '../../types/vehicle.types';

export const useGetVehicles = () => {
  return useQuery({
    queryKey: ['vehicles'],
    queryFn: async () => {
      const data = await http.get<VehicleResponse[]>('/vehicles');
      return data;
    },
  });
};