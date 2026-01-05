import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { EmployeeResponse } from '../../types/employee.types';
import type { UpdateEmployeeFormData } from '../../validations/employess/updateEmployeeSchema';

export const useUpdateEmployee = (employeeId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: UpdateEmployeeFormData) => {
      const response = await http.put<EmployeeResponse>(`/employees/${employeeId}`, data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['company-employees'] });
      queryClient.invalidateQueries({ queryKey: ['employees', employeeId] });
      queryClient.invalidateQueries({ queryKey: ['company-summary'] });
    },
  });
};