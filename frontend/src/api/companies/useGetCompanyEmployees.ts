import { useQuery } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { EmployeeResponse } from '../../types/employee.types';

export const useGetCompanyEmployees = (companyId: number) => {
  return useQuery({
    queryKey: ['company-employees', companyId],
    queryFn: async () => {
      const data = await http.get<EmployeeResponse[]>(`/companies/${companyId}/employees`);
      return data;
    },
    enabled: !!companyId,
  });
};