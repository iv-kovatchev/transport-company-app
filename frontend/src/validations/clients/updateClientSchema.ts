import { z } from 'zod';

export const updateClientSchema = z.object({
  name: z.string()
    .min(2, 'Name must be between 2 and 100 characters')
    .max(100, 'Name must be between 2 and 100 characters'),
  phone: z.string()
    .regex(/^\+?[0-9]{10,15}$/, 'Phone must be 10-15 digits, optionally starting with +')
    .max(20, 'Phone must not exceed 20 characters')
    .optional()
    .or(z.literal('')),
  email: z.string()
    .min(1, 'Email is required')
    .email('Email should be valid')
    .max(100, 'Email must not exceed 100 characters'),
  address: z.string()
    .max(200, 'Address must not exceed 200 characters')
    .optional()
    .or(z.literal('')),
});

export type UpdateClientFormData = z.infer<typeof updateClientSchema>;
