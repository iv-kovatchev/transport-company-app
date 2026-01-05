import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { TransportResponse } from '../../types/transport.types';
import type { UpdateTransportFormData } from '../../validations/tansports/updateTransportSchema';

export const useUpdateTransport = (transportId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: UpdateTransportFormData) => {
      const response = await http.put<TransportResponse>(`/transports/${transportId}`, data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-transports'] });
      queryClient.invalidateQueries({ queryKey: ['transports', transportId] });
      queryClient.invalidateQueries({ queryKey: ['company-summary'] });
    },
  });
};
