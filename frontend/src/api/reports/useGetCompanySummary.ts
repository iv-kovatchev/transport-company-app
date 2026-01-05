import { useQuery } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { CompanySummaryReport } from '../../types/report.types';

export const useGetCompanySummary = (companyId: number) => {
  return useQuery({
    queryKey: ['company-summary', companyId],
    queryFn: async () => {
      const data = await http.get<CompanySummaryReport>(`/reports/companies/${companyId}/summary`);
      return data;
    },
    enabled: !!companyId,
  });
};