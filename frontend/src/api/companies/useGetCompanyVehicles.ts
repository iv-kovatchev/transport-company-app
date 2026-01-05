import { useQuery } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { VehicleResponse } from '../../types/vehicle.types';

export const useGetCompanyVehicles = (companyId: number) => {
  return useQuery({
    queryKey: ['company-vehicles', companyId],
    queryFn: async () => {
      const data = await http.get<VehicleResponse[]>(`/companies/${companyId}/vehicles`);
      return data;
    },
    enabled: !!companyId,
  });
};