import { z } from 'zod';

export const createEmployeeSchema = z.object({
  firstName: z.string()
    .min(1, 'First name is required')
    .max(100, 'First name must not exceed 100 characters'),
  lastName: z.string()
    .min(1, 'Last name is required')
    .max(100, 'Last name must not exceed 100 characters'),
  phone: z.string()
    .max(20, 'Phone must not exceed 20 characters')
    .optional()
    .or(z.literal('')),
  email: z.string()
    .email('Email must be valid')
    .max(100, 'Email must not exceed 100 characters')
    .optional()
    .or(z.literal('')),
  salary: z.number()
    .min(0, 'Salary must be positive'),
  companyId: z.number(),
});

export type CreateEmployeeFormData = z.infer<typeof createEmployeeSchema>;