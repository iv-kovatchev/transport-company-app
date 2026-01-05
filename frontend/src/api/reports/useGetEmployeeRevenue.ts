import { useQuery } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { EmployeeRevenueReport } from '../../types/report.types';

export const useGetEmployeeRevenue = (
  employeeId: number,
  startDate: string,
  endDate: string
) => {
  return useQuery({
    queryKey: ['employee-revenue', employeeId, startDate, endDate],
    queryFn: async () => {
      const url = `/reports/employees/${employeeId}/revenue?startDate=${startDate}&endDate=${endDate}`;
      const data = await http.get<EmployeeRevenueReport>(url);
      return data;
    },
    enabled: !!employeeId && !!startDate && !!endDate,
  });
};