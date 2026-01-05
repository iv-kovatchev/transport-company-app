export interface CompanySummaryReport {
  companyId: number;
  companyName: string;
  totalTransports: number;
  totalRevenue: number;
  paidTransports: number;
  unpaidTransports: number;
  paidRevenue: number;
  unpaidRevenue: number;
  totalVehicles: number;
  totalEmployees: number;
}

export interface RevenueByPeriodReport {
  companyId: number;
  companyName: string;
  startDate: string;
  endDate: string;
  totalRevenue: number;
  paidRevenue: number;
  unpaidRevenue: number;
  transportsCount: number;
}

export interface DriverPerformanceReport {
  driverId: number;
  driverName: string;
  totalTransports: number;
  totalRevenue: number;
  averageRevenuePerTransport: number;
}

export interface EmployeeRevenueReport {
  employeeId: number;
  employeeName: string;
  startDate: string;
  endDate: string;
  totalTransports: number;
  totalRevenue: number;
  averageRevenuePerTransport: number;
}