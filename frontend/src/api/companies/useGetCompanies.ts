import { useQuery } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { CompanyResponse } from '../../types/company.types';

export const useGetCompanies = () => {
  return useQuery({
    queryKey: ['companies'],
    queryFn: async () => {
      const data = await http.get<CompanyResponse[]>('/companies');
      return data;
    },
  });
};