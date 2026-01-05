import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { QualificationType } from '../../types/employee.types';

export const useDeleteQualification = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({ employeeId, qualificationType }: { employeeId: number; qualificationType: QualificationType }) => {
      await http.delete(`/employees/${employeeId}/qualifications/${qualificationType}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-employees'] });
    },
  });
};