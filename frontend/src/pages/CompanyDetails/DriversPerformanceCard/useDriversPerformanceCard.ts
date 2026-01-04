import { useState, useEffect } from 'react';
import type { DriverPerformanceReport } from '../../../types/report.types';

interface UseDriversPerformanceCardProps {
  drivers: DriverPerformanceReport[];
}

export const useDriversPerformanceCard = ({ drivers }: UseDriversPerformanceCardProps) => {
  const [selectedDriverId, setSelectedDriverId] = useState<number | null>(null);

  // Set first driver as default when data loads
  useEffect(() => {
    if (drivers && drivers.length > 0 && selectedDriverId === null) {
      setSelectedDriverId(drivers[0].driverId);
    }
  }, [drivers, selectedDriverId]);

  const selectedDriver = drivers?.find(d => d.driverId === selectedDriverId) || null;

  const handleDriverChange = (driverId: number) => {
    setSelectedDriverId(driverId);
  };

  return {
    selectedDriver,
    handleDriverChange,
  };
};