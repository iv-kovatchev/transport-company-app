import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { EmployeeResponse } from '../../types/employee.types';
import type { CreateEmployeeFormData } from '../../validations/employess/createEmployeeSchema';

export const useCreateEmployee = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: CreateEmployeeFormData) => {
      const response = await http.post<EmployeeResponse>('/employees', data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-employees'] });
      queryClient.invalidateQueries({ queryKey: ['company-summary'] });
    },
  });
};