export interface EmployeeResponse {
  id: number;
  firstName: string;
  lastName: string;
  phone: string | null;
  email: string | null;
  salary: number;
  companyId: number;
  qualifications: QualificationResponse[];
  createdAt: string;
  updatedAt: string;
}

export type QualificationType =
  | 'HAZARDOUS_MATERIALS'
  | 'PASSENGER_TRANSPORT_12_PLUS'
  | 'LONG_DISTANCE'
  | 'INTERNATIONAL'
  | 'HEAVY_CARGO'
  | 'TANKER_TRANSPORT';

export interface QualificationResponse {
  id: number;
  employeeId: number;
  qualificationType: QualificationType;
  createdAt: string;
}