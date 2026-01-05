import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';

export const useDeleteClient = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (clientId: number) => {
      await http.delete(`/clients/${clientId}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-clients'] });
      queryClient.invalidateQueries({ queryKey: ['company-summary'] });
    },
  });
};
