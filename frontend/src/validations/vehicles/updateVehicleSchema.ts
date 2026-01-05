import { z } from 'zod';

export const updateVehicleSchema = z.object({
  licensePlate: z.string()
    .min(1, 'License plate is required')
    .max(20, 'License plate must not exceed 20 characters'),
  type: z.union([
    z.literal('TRUCK'),
    z.literal('BUS'),
    z.literal('VAN'),
    z.literal('CISTERN')
  ], { message: 'Vehicle type is required' }),
  brand: z.string()
    .max(100, 'Brand must not exceed 100 characters')
    .optional()
    .or(z.literal('')),
  model: z.string()
    .max(100, 'Model must not exceed 100 characters')
    .optional()
    .or(z.literal('')),
  year: z.number()
    .min(1900, 'Year must be 1900 or later')
    .max(2100, 'Year must be 2100 or earlier')
    .optional()
    .nullable(),
  capacityKg: z.number()
    .min(0, 'Capacity in kg must be positive')
    .optional()
    .nullable(),
  capacityPassengers: z.number()
    .min(0, 'Passenger capacity must be positive')
    .optional()
    .nullable(),
});

export type UpdateVehicleFormData = z.infer<typeof updateVehicleSchema>;
