import { useMutation, useQueryClient } from '@tanstack/react-query';
import { http } from '../../services/http';
import type { CompanyResponse } from '../../types/company.types';
import type { UpdateCompanyFormData } from '../../validations/companies/updateCompanySchema';

export const useUpdateCompany = (companyId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: UpdateCompanyFormData) => {
      const response = await http.put<CompanyResponse>(`/companies/${companyId}`, data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['companies'] });
      queryClient.invalidateQueries({ queryKey: ['companies', companyId] });
    },
  });
};