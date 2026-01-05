import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { TransportResponse } from '../../types/transport.types';
import type { CreateTransportFormData } from '../../validations/tansports/createTransportSchema';

export const useCreateTransport = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: CreateTransportFormData) => {
      const response = await http.post<TransportResponse>('/transports', data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-transports'] });
      queryClient.invalidateQueries({ queryKey: ['company-summary'] });
    },
  });
};
