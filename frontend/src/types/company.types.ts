export interface CompanyResponse {
  id: number;
  name: string;
  registrationNumber: string | null;
  address: string | null;
  phone: string | null;
  email: string | null;
  createdAt: string;
  updatedAt: string;
}