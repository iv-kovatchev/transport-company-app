export interface ClientResponse {
  id: number;
  name: string;
  phone: string | null;
  email: string | null;
  address: string | null;
  companyId: number;
  createdAt: string;
  updatedAt: string;
}