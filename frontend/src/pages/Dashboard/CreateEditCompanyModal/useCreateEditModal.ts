import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { createCompanySchema, type CreateCompanyFormData } from '../../../validations/companies/createCompanySchema';
import { useCreateCompany } from '../../../api/companies/useCreateCompany';
import type { CompanyResponse } from '../../../types/company.types';
import { updateCompanySchema, type UpdateCompanyFormData } from '../../../validations/companies/updateCompanySchema';
import { useUpdateCompany } from '../../../api/companies/useUpdateCompany';

interface UseCreateEditModalProps {
    open: boolean;
    onClose: () => void;
    company?: CompanyResponse;
}

export const useCreateEditModal = ({ open, onClose, company }: UseCreateEditModalProps) => {
    const mode = company ? 'edit' : 'create';

    const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
        open: false,
        message: '',
        severity: 'success',
    });

    const { mutate: createCompany, isPending: isCreating } = useCreateCompany();
    const { mutate: updateCompany, isPending: isUpdating } = useUpdateCompany(company?.id || 0);

    const isPending = isCreating || isUpdating;

    const { control, handleSubmit, reset, formState: { errors } } = useForm<CreateCompanyFormData | UpdateCompanyFormData>({
        resolver: zodResolver(mode === 'edit' ? updateCompanySchema : createCompanySchema),
        defaultValues: {
            name: '',
            registrationNumber: '',
            address: '',
            phone: '',
            email: '',
        },
    });

    // Reset form when modal closes
    useEffect(() => {
        if (!open) {
            reset({
                name: '',
                registrationNumber: '',
                address: '',
                phone: '',
                email: '',
            });
        }
    }, [open, reset]);

    // Populate form with company data in edit mode
    useEffect(() => {
        if (mode === 'edit' && company && open) {
            reset({
                name: company.name,
                registrationNumber: company.registrationNumber || '',
                address: company.address || '',
                phone: company.phone || '',
                email: company.email || '',
            });
        }
    }, [mode, company, open, reset]);

    const onSubmit = (data: CreateCompanyFormData | UpdateCompanyFormData) => {
        const cleanedData = Object.fromEntries(
            Object.entries(data).filter(([_, value]) => value !== '')
        ) as CreateCompanyFormData | UpdateCompanyFormData;

        if (mode === 'edit') {
            updateCompany(cleanedData as UpdateCompanyFormData, {
                onSuccess: () => {
                    setSnackbar({ open: true, message: 'Company updated successfully!', severity: 'success' });
                    onClose();
                    reset();
                },
                onError: (error: any) => {
                    const message = error?.message || 'Failed to update company';
                    setSnackbar({ open: true, message, severity: 'error' });
                },
            });
        } else {
            createCompany(cleanedData as CreateCompanyFormData, {
                onSuccess: () => {
                    setSnackbar({ open: true, message: 'Company created successfully!', severity: 'success' });
                    onClose();
                    reset();
                },
                onError: (error: any) => {
                    const message = error?.message || 'Failed to create company';
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