import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';

export const useDeleteTransport = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (transportId: number) => {
      await http.delete(`/transports/${transportId}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-transports'] });
      queryClient.invalidateQueries({ queryKey: ['company-summary'] });
    },
  });
};
