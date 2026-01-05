import { useQuery } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { TransportResponse } from '../../types/transport.types';

export const useGetCompanyTransports = (companyId: number) => {
  return useQuery({
    queryKey: ['company-transports', companyId],
    queryFn: async () => {
      const data = await http.get<TransportResponse[]>(`/companies/${companyId}/transports`);
      return data;
    },
    enabled: !!companyId,
  });
};