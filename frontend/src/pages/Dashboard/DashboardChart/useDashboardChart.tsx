import { useState } from "react";
import type { TransportResponse } from "../../../types/transport.types";
import type { VehicleResponse } from "../../../types/vehicle.types";

import { BarChart } from '@mui/x-charts/BarChart';
import { LineChart } from '@mui/x-charts/LineChart';
import { PieChart } from '@mui/x-charts/PieChart';

type ChartType = 'transports' | 'revenue' | 'vehicles' | 'payments';

interface UseDashboardChartProps {
  transports: TransportResponse[];
  vehicles: VehicleResponse[];
}

const useDashboardChart = ({ transports, vehicles }: UseDashboardChartProps) => {
  const [chartType, setChartType] = useState<ChartType>('transports');

  // Prepare data for Transports per Month
  const getTransportsPerMonth = () => {
    const monthCounts: Record<string, number> = {};

    transports.forEach(transport => {
      // Parse Java LocalDateTime array format [year, month, day, hour, minute, second]
      const dateArray = transport.departureDate;
      const date = Array.isArray(dateArray)
        ? new Date(dateArray[0], dateArray[1] - 1, dateArray[2])
        : new Date(transport.departureDate);

      if (!isNaN(date.getTime())) {
        const monthYear = `${date.toLocaleString('default', { month: 'short' })} ${date.getFullYear()}`;
        monthCounts[monthYear] = (monthCounts[monthYear] || 0) + 1;
      }
    });

    const entries = Object.entries(monthCounts).slice(-6);
    return {
      xAxis: entries.map(([month]) => month),
      series: [{ data: entries.map(([, count]) => count), label: 'Transports' }],
    };
  };

  // Prepare data for Revenue per Month
  const getRevenuePerMonth = () => {
    const monthRevenue: Record<string, number> = {};

    transports.forEach(transport => {
      // Parse Java LocalDateTime array format [year, month, day, hour, minute, second]
      const dateArray = transport.departureDate;
      const date = Array.isArray(dateArray)
        ? new Date(dateArray[0], dateArray[1] - 1, dateArray[2])
        : new Date(transport.departureDate);

      if (!isNaN(date.getTime())) {
        const monthYear = `${date.toLocaleString('default', { month: 'short' })} ${date.getFullYear()}`;
        monthRevenue[monthYear] = (monthRevenue[monthYear] || 0) + transport.price;
      }
    });

    const entries = Object.entries(monthRevenue).slice(-6);
    return {
      xAxis: entries.map(([month]) => month),
      series: [{ data: entries.map(([, revenue]) => Number(revenue.toFixed(2))), label: 'Revenue (BGN)' }],
    };
  };

  // Prepare data for Transports by Vehicle Type
  const getTransportsByVehicleType = () => {
    // Create a map of vehicleId -> vehicle type
    const vehicleTypeMap = new Map<number, string>();
    vehicles.forEach(vehicle => {
      vehicleTypeMap.set(vehicle.id, vehicle.type);
    });

    // Count transports by vehicle type
    const typeCounts: Record<string, number> = {
      'BUS': 0,
      'TRUCK': 0,
      'TANKER': 0,
      'VAN': 0,
      'CAR': 0,
    };

    transports.forEach(transport => {
      const vehicleType = vehicleTypeMap.get(transport.vehicleId);
      if (vehicleType && typeCounts[vehicleType] !== undefined) {
        typeCounts[vehicleType]++;
      }
    });

    // Convert to chart data format
    return Object.entries(typeCounts)
      .filter(([, count]) => count > 0) // Only include types with transports
      .map(([type, count], index) => ({
        id: index,
        value: count,
        label: type,
      }));
  };

  // Prepare data for Paid vs Unpaid
  const getPaymentStatus = () => {
    const paid = transports.filter(t => t.isPaid).length;
    const unpaid = transports.filter(t => !t.isPaid).length;

    return [
      { id: 0, value: paid, label: 'Paid' },
      { id: 1, value: unpaid, label: 'Unpaid' },
    ];
  };

  const renderChart = () => {
    switch (chartType) {
      case 'transports': {
        const data = getTransportsPerMonth();
        return (
          <BarChart
            xAxis={[{ scaleType: 'band', data: data.xAxis }]}
            series={data.series}
            height={300}
          />
        );
      }

      case 'revenue': {
        const data = getRevenuePerMonth();
        return (
          <LineChart
            xAxis={[{ scaleType: 'band', data: data.xAxis }]}
            series={data.series}
            height={300}
          />
        );
      }

      case 'vehicles':
        return (
          <PieChart
            series={[{ data: getTransportsByVehicleType() }]}
            height={300}
          />
        );

      case 'payments':
        return (
          <PieChart
            series={[{ data: getPaymentStatus() }]}
            height={300}
          />
        );

      default:
        return null;
    }
  };

  return {
    chartType,
    setChartType,
    renderChart,
  }
};

export default useDashboardChart;