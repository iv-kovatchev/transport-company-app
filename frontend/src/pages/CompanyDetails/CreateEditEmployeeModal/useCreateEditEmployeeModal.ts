import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { useCreateEmployee } from '../../../api/employees/useCreateEmployee';
import { useUpdateEmployee } from '../../../api/employees/useUpdateEmployee';
import type { EmployeeResponse } from '../../../types/employee.types';
import { createEmployeeSchema, type CreateEmployeeFormData } from '../../../validations/employess/createEmployeeSchema';
import { updateEmployeeSchema, type UpdateEmployeeFormData } from '../../../validations/employess/updateEmployeeSchema';

interface UseCreateEditEmployeeModalProps {
  open: boolean;
  onClose: () => void;
  employee?: EmployeeResponse;
  companyId: number;
}

export const useCreateEditEmployeeModal = ({ open, onClose, employee, companyId }: UseCreateEditEmployeeModalProps) => {
  const mode = employee ? 'edit' : 'create';

  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
    open: false,
    message: '',
    severity: 'success',
  });

  const { mutate: createEmployee, isPending: isCreating } = useCreateEmployee();
  const { mutate: updateEmployee, isPending: isUpdating } = useUpdateEmployee(employee?.id || 0);

  const isPending = isCreating || isUpdating;

  const { control, handleSubmit, reset, formState: { errors } } = useForm<CreateEmployeeFormData | UpdateEmployeeFormData>({
    resolver: zodResolver(mode === 'edit' ? updateEmployeeSchema : createEmployeeSchema),
    defaultValues: {
      firstName: '',
      lastName: '',
      phone: '',
      email: '',
      salary: 0,
      ...(mode === 'create' && { companyId }),
    },
  });

  // Reset form when modal closes
  useEffect(() => {
    if (!open) {
      reset({
        firstName: '',
        lastName: '',
        phone: '',
        email: '',
        salary: 0,
        ...(mode === 'create' && { companyId }),
      });
    }
  }, [open, reset, mode, companyId]);

  // Populate form with employee data in edit mode
  useEffect(() => {
    if (mode === 'edit' && employee && open) {
      reset({
        firstName: employee.firstName,
        lastName: employee.lastName,
        phone: employee.phone || '',
        email: employee.email || '',
        salary: employee.salary,
      });
    }
  }, [mode, employee, open, reset]);

  const onSubmit = (data: CreateEmployeeFormData | UpdateEmployeeFormData) => {
    const cleanedData = Object.fromEntries(
      Object.entries(data).filter(([_, value]) => value !== '')
    ) as CreateEmployeeFormData | UpdateEmployeeFormData;

    if (mode === 'edit') {
      updateEmployee(cleanedData as UpdateEmployeeFormData, {
        onSuccess: () => {
          setSnackbar({ open: true, message: 'Employee updated successfully!', severity: 'success' });
          onClose();
          reset();
        },
        onError: (error: any) => {
          const message = error?.message || 'Failed to update employee';
          setSnackbar({ open: true, message, severity: 'error' });
        },
      });
    } else {
      createEmployee(cleanedData as CreateEmployeeFormData, {
        onSuccess: () => {
          setSnackbar({ open: true, message: 'Employee created successfully!', severity: 'success' });
          onClose();
          reset();
        },
        onError: (error: any) => {
          const message = error?.message || 'Failed to create employee';
          setSnackbar({ open: true, message, severity: 'error' });
        },
      });
    }
  };

  const handleCloseSnackbar = () => {
    setSnackbar({ ...snackbar, open: false });
  };

  return {
    control,
    handleSubmit: handleSubmit(onSubmit),
    errors,
    isPending,
    snackbar,
    handleCloseSnackbar,
    mode,
  };
};
