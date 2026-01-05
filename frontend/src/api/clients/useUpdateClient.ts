import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { ClientResponse } from '../../types/client.types';
import type { UpdateClientFormData } from '../../validations/clients/updateClientSchema';

export const useUpdateClient = (clientId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: UpdateClientFormData) => {
      const response = await http.put<ClientResponse>(`/clients/${clientId}`, data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-clients'] });
      queryClient.invalidateQueries({ queryKey: ['clients', clientId] });
      queryClient.invalidateQueries({ queryKey: ['company-summary'] });
    },
  });
};