import { z } from 'zod';

export const updateCompanySchema = z.object({
    name: z.string()
        .min(1, 'Name is required')
        .max(255, 'Name cannot exceed 255 characters'),

    registrationNumber: z.string()
        .max(50, 'Registration number cannot exceed 50 characters')
        .optional()
        .or(z.literal('')),

    address: z.string()
        .max(500, 'Address cannot exceed 500 characters')
        .optional()
        .or(z.literal('')),

    phone: z.string()
        .refine(
            (val) => val === '' || /^\+?[0-9]{10,15}$/.test(val),
            'Phone must be 10-15 digits, optionally starting with +'
        )
        .optional()
        .or(z.literal('')),

    email: z.string()
        .refine(
            (val) => val === '' || z.string().email().safeParse(val).success,
            'Email should be valid'
        )
        .max(100, 'Email cannot exceed 100 characters')
        .optional()
        .or(z.literal('')),
});

export type UpdateCompanyFormData = z.infer<typeof updateCompanySchema>;