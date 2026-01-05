import { useQuery } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { ClientResponse } from '../../types/client.types';

export const useGetCompanyClients = (companyId: number) => {
  return useQuery({
    queryKey: ['company-clients', companyId],
    queryFn: async () => {
      const data = await http.get<ClientResponse[]>(`/companies/${companyId}/clients`);
      return data;
    },
    enabled: !!companyId,
  });
};