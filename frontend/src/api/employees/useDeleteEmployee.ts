import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';

export const useDeleteEmployee = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (employeeId: number) => {
      await http.delete(`/employees/${employeeId}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-employees'] });
      queryClient.invalidateQueries({ queryKey: ['company-summary'] });
    },
  });
};
