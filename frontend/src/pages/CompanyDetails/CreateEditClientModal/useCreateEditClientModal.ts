import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { createClientSchema, type CreateClientFormData } from '../../../validations/clients/createClientSchema';
import { updateClientSchema, type UpdateClientFormData } from '../../../validations/clients/updateClientSchema';
import { useCreateClient } from '../../../api/clients/useCreateClient';
import { useUpdateClient } from '../../../api/clients/useUpdateClient';
import type { ClientResponse } from '../../../types/client.types';

interface UseCreateEditClientModalProps {
    open: boolean;
    onClose: () => void;
    client?: ClientResponse;
    companyId: number;
}

export const useCreateEditClientModal = ({ open, onClose, client, companyId }: UseCreateEditClientModalProps) => {
    const mode = client ? 'edit' : 'create';

    const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
        open: false,
        message: '',
        severity: 'success',
    });

    const { mutate: createClient, isPending: isCreating } = useCreateClient();
    const { mutate: updateClient, isPending: isUpdating } = useUpdateClient(client?.id || 0);

    const isPending = isCreating || isUpdating;

    const { control, handleSubmit, reset, formState: { errors } } = useForm<CreateClientFormData | UpdateClientFormData>({
        resolver: zodResolver(mode === 'edit' ? updateClientSchema : createClientSchema),
        defaultValues: {
            name: '',
            phone: '',
            email: '',
            address: '',
            ...(mode === 'create' && { companyId }),
        },
    });

    // Reset form when modal closes
    useEffect(() => {
        if (!open) {
            reset({
                name: '',
                phone: '',
                email: '',
                address: '',
                ...(mode === 'create' && { companyId }),
            });
        }
    }, [open, reset, mode, companyId]);

    // Populate form with client data in edit mode
    useEffect(() => {
        if (mode === 'edit' && client && open) {
            reset({
                name: client.name,
                phone: client.phone || '',
                email: client.email || '',
                address: client.address || '',
            });
        }
    }, [mode, client, open, reset]);

    const onSubmit = (data: CreateClientFormData | UpdateClientFormData) => {
        const cleanedData = Object.fromEntries(
            Object.entries(data).filter(([_, value]) => value !== '')
        ) as CreateClientFormData | UpdateClientFormData;

        if (mode === 'edit') {
            updateClient(cleanedData as UpdateClientFormData, {
                onSuccess: () => {
                    setSnackbar({ open: true, message: 'Client updated successfully!', severity: 'success' });
                    onClose();
                    reset();
                },
                onError: (error: any) => {
                    const message = error?.message || 'Failed to update client';
                    setSnackbar({ open: true, message, severity: 'error' });
                },
            });
        } else {
            createClient(cleanedData as CreateClientFormData, {
                onSuccess: () => {
                    setSnackbar({ open: true, message: 'Client created successfully!', severity: 'success' });
                    onClose();
                    reset();
                },
                onError: (error: any) => {
                    const message = error?.message || 'Failed to create client';
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