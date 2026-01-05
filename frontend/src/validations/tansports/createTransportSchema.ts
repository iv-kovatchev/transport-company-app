import { z } from 'zod';

export const createTransportSchema = z.object({
  companyId: z.number(),
  clientId: z.number().min(1, 'Client is required'),
  vehicleId: z.number().min(1, 'Vehicle is required'),
  driverId: z.number().min(1, 'Driver is required'),
  cargoType: z.union([
    z.literal('GOODS'),
    z.literal('PASSENGERS')
  ], { message: 'Cargo type is required' }),
  cargoName: z.string()
    .max(255, 'Cargo name must not exceed 255 characters')
    .optional()
    .or(z.literal('')),
  cargoWeightKg: z.number()
    .min(0, 'Cargo weight must be positive')
    .optional()
    .nullable(),
  passengerCount: z.number()
    .min(0, 'Passenger count must be positive')
    .optional()
    .nullable(),
  startLocation: z.string()
    .min(1, 'Start location is required')
    .max(255, 'Start location must not exceed 255 characters'),
  endLocation: z.string()
    .min(1, 'End location is required')
    .max(255, 'End location must not exceed 255 characters'),
  departureDate: z.string().min(1, 'Departure date is required'),
  arrivalDate: z.string().optional().or(z.literal('')),
  price: z.number()
    .min(0, 'Price must be positive'),
}).refine(
  (data) => {
    if (data.cargoType === 'GOODS') {
      return !!data.cargoName || (data.cargoWeightKg !== null && data.cargoWeightKg !== undefined);
    }
    return true;
  },
  {
    message: 'For GOODS transport, please provide cargo name or weight',
    path: ['cargoName'],
  }
).refine(
  (data) => {
    if (data.cargoType === 'PASSENGERS') {
      return data.passengerCount !== null && data.passengerCount !== undefined && data.passengerCount > 0;
    }
    return true;
  },
  {
    message: 'For PASSENGERS transport, passenger count is required',
    path: ['passengerCount'],
  }
);

export type CreateTransportFormData = z.infer<typeof createTransportSchema>;