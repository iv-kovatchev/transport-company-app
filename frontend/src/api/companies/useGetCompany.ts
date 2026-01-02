import { useQuery } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { CompanyResponse } from '../../types/company.types';

export const useGetCompany = (id: number) => {
  return useQuery({
    queryKey: ['companies', id],
    queryFn: async () => {
      const data = await http.get<CompanyResponse>(`/companies/${id}`);
      return data;
    },
    enabled: !!id,
  });
};