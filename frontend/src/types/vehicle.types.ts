export type VehicleType = 'TRUCK' | 'BUS' | 'VAN' | 'CISTERN';

export interface VehicleResponse {
  id: number;
  licensePlate: string;
  type: VehicleType;
  brand: string | null;
  model: string | null;
  year: number | null;
  capacityKg: number | null;
  capacityPassengers: number | null;
  companyId: number;
  createdAt: string;
  updatedAt: string;
}