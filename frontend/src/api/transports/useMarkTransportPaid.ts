import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { TransportResponse } from '../../types/transport.types';

export const useMarkTransportPaid = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (transportId: number) => {
      const response = await http.put<TransportResponse>(`/transports/${transportId}/mark-paid`);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-transports'] });
      queryClient.invalidateQueries({ queryKey: ['company-summary'] });
    },
  });
};
