import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { useCreateEmployee } from '../../../api/employees/useCreateEmployee';
import { useUpdateEmployee } from '../../../api/employees/useUpdateEmployee';
import { useCreateQualification } from '../../../api/qualifications/useCreateQualification';
import { useDeleteQualification } from '../../../api/qualifications/useDeleteQualification';
import type { EmployeeResponse, QualificationType } from '../../../types/employee.types';
import { createEmployeeSchema, type CreateEmployeeFormData } from '../../../validations/employess/createEmployeeSchema';
import { updateEmployeeSchema, type UpdateEmployeeFormData } from '../../../validations/employess/updateEmployeeSchema';

interface UseCreateEditEmployeeModalProps {
  open: boolean;
  onClose: () => void;
  employee?: EmployeeResponse;
  companyId: number;
}

const allQualificationTypes: QualificationType[] = [
  'HAZARDOUS_MATERIALS',
  'PASSENGER_TRANSPORT_12_PLUS',
  'LONG_DISTANCE',
  'INTERNATIONAL',
  'HEAVY_CARGO',
  'TANKER_TRANSPORT',
];

export const useCreateEditEmployeeModal = ({ open, onClose, employee, companyId }: UseCreateEditEmployeeModalProps) => {
  const mode = employee ? 'edit' : 'create';

  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
    open: false,
    message: '',
    severity: 'success',
  });

  const [selectedQualifications, setSelectedQualifications] = useState<QualificationType[]>([]);

  const { mutate: createEmployee, isPending: isCreating } = useCreateEmployee();
  const { mutate: updateEmployee, isPending: isUpdating } = useUpdateEmployee(employee?.id || 0);
  const { mutate: createQualification } = useCreateQualification();
  const { mutate: deleteQualification } = useDeleteQualification();

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
      setSelectedQualifications([]);
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

      // Set existing qualifications
      const existingQualifications = employee.qualifications.map(q => q.qualificationType);
      setSelectedQualifications(existingQualifications);
    }
  }, [mode, employee, open, reset]);

  const handleQualificationToggle = (qualificationType: QualificationType) => {
    setSelectedQualifications(prev => {
      if (prev.includes(qualificationType)) {
        return prev.filter(q => q !== qualificationType);
      } else {
        return [...prev, qualificationType];
      }
    });
  };

  const handleQualifications = async (employeeId: number) => {
    try {
      const promises: Promise<any>[] = [];

      if (mode === 'edit' && employee) {
        const existingQualifications = employee.qualifications.map(q => q.qualificationType);
        const toAdd = selectedQualifications.filter(q => !existingQualifications.includes(q));
        const toRemove = existingQualifications.filter(q => !selectedQualifications.includes(q));

        // Add new qualifications
        toAdd.forEach(qualificationType => {
          promises.push(
            new Promise((resolve, reject) => {
              createQualification(
                { employeeId, qualificationType },
                { onSuccess: resolve, onError: reject }
              );
            })
          );
        });

        // Remove old qualifications
        toRemove.forEach(qualificationType => {
          promises.push(
            new Promise((resolve, reject) => {
              deleteQualification(
                { employeeId, qualificationType },
                { onSuccess: resolve, onError: reject }
              );
            })
          );
        });
      } else {
        // Create mode - add all selected qualifications
        selectedQualifications.forEach(qualificationType => {
          promises.push(
            new Promise((resolve, reject) => {
              createQualification(
                { employeeId, qualificationType },
                { onSuccess: resolve, onError: reject }
              );
            })
          );
        });
      }

      await Promise.all(promises);
    } catch (error: any) {
      const message = error?.response?.data?.message || error?.message || 'Failed to update qualifications';
      setSnackbar({ open: true, message, severity: 'error' });
      throw error; // Re-throw to prevent success message
    }
  };

  const onSubmit = async (data: CreateEmployeeFormData | UpdateEmployeeFormData) => {
    const cleanedData = Object.fromEntries(
      Object.entries(data).filter(([_, value]) => value !== '')
    ) as CreateEmployeeFormData | UpdateEmployeeFormData;

    if (mode === 'edit') {
      updateEmployee(cleanedData as UpdateEmployeeFormData, {
        onSuccess: async () => {
          try {
            await handleQualifications(employee!.id);
            setSnackbar({ open: true, message: 'Employee updated successfully!', severity: 'success' });
            onClose();
            reset();
          } catch (error) {
            // Error already handled in handleQualifications
          }
        },
        onError: (error: any) => {
          const message = error?.message || 'Failed to update employee';
          setSnackbar({ open: true, message, severity: 'error' });
        },
      });
    } else {
      createEmployee(cleanedData as CreateEmployeeFormData, {
        onSuccess: async (data) => {
          try {
            await handleQualifications(data.id);
            setSnackbar({ open: true, message: 'Employee created successfully!', severity: 'success' });
            onClose();
            reset();
          } catch (error) {
            // Error already handled in handleQualifications
          }
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
    allQualificationTypes,
    selectedQualifications,
    handleQualificationToggle,
  };
};
