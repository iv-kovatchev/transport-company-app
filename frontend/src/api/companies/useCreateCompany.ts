import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { CompanyResponse } from '../../types/company.types';
import type { CreateCompanyFormData } from '../../validations/companies/createCompanySchema';

export const useCreateCompany = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: CreateCompanyFormData) => {
      const response = await http.post<CompanyResponse>('/companies', data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['companies'] });
    },
  });
};