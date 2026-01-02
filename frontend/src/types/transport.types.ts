export type CargoType = 'GOODS' | 'PASSENGERS';

export interface TransportResponse {
  id: number;
  companyId: number;
  clientId: number;
  vehicleId: number;
  driverId: number;
  cargoType: CargoType;
  cargoName: string | null;
  cargoWeightKg: number | null;
  passengerCount: number | null;
  startLocation: string;
  endLocation: string;
  departureDate: string;
  arrivalDate: string | null;
  price: number;
  isPaid: boolean;
  paymentDate: string | null;
  createdAt: string;
  updatedAt: string;
}