import { useQuery } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { DriverPerformanceReport } from '../../types/report.types';

export const useGetDriversPerformance = (companyId: number) => {
  return useQuery({
    queryKey: ['drivers-performance', companyId],
    queryFn: async () => {
      const data = await http.get<DriverPerformanceReport[]>(
        `/reports/companies/${companyId}/drivers-performance`
      );
      return data;
    },
    enabled: !!companyId,
  });
};