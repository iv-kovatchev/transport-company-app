import { useQuery } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { TransportResponse } from '../../types/transport.types';

export const useGetTransports = () => {
  return useQuery({
    queryKey: ['transports'],
    queryFn: async () => {
      const data = await http.get<TransportResponse[]>('/transports');
      return data;
    },
  });
};