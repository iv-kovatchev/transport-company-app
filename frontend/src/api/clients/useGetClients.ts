import { useQuery } from "@tanstack/react-query";
import type { ClientResponse } from "../../types/client.types";
import { http } from "../../services/http";

export const useGetClients = () => {
  return useQuery({
    queryKey: ['clients'],
    queryFn: async () => {
      const data = await http.get<ClientResponse[]>('/clients');
      return data;
    },
  });
};