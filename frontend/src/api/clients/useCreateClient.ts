import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { ClientResponse } from '../../types/client.types';
import type { CreateClientFormData } from '../../validations/clients/createClientSchema';

export const useCreateClient = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: CreateClientFormData) => {
      const response = await http.post<ClientResponse>('/clients', data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-clients'] });
      queryClient.invalidateQueries({ queryKey: ['company-summary'] });
    },
  });
};
