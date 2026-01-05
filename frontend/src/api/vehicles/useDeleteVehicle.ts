import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';

export const useDeleteVehicle = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (vehicleId: number) => {
      await http.delete(`/vehicles/${vehicleId}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-vehicles'] });
      queryClient.invalidateQueries({ queryKey: ['company-summary'] });
    },
  });
};
