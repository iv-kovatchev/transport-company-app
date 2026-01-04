import { useQuery } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { RevenueByPeriodReport } from '../../types/report.types';

export const useGetCompanyRevenue = (
  companyId: number,
  startDate: string,
  endDate: string
) => {
  return useQuery({
    queryKey: ['company-revenue', companyId, startDate, endDate],
    queryFn: async () => {
      const url = `/reports/companies/${companyId}/revenue?startDate=${startDate}&endDate=${endDate}`;
      const data = await http.get<RevenueByPeriodReport>(url);
      return data;
    },
    enabled: !!companyId && !!startDate && !!endDate,
  });
};