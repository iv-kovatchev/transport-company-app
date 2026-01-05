import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { QualificationResponse, QualificationType } from '../../types/employee.types';

interface CreateQualificationRequest {
  employeeId: number;
  qualificationType: QualificationType;
}

export const useCreateQualification = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: CreateQualificationRequest) => {
      const response = await http.post<QualificationResponse>('/qualifications', data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-employees'] });
    },
  });
};