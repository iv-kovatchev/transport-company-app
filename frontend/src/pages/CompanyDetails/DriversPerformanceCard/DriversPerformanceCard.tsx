import { CardContent, Typography, Select, MenuItem, FormControl, InputLabel, Box } from '@mui/material';
import LocalShippingIcon from '@mui/icons-material/LocalShipping';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import TrendingUpIcon from '@mui/icons-material/TrendingUp';
import { DriversStyledCard, DriverStatItem, DriverStatContent } from './DriversPerformanceCard.styles';
import { useDriversPerformanceCard } from './useDriversPerformanceCard';
import type { DriverPerformanceReport } from '../../../types/report.types';

interface DriversPerformanceCardProps {
  drivers: DriverPerformanceReport[];
}

export const DriversPerformanceCard = ({ drivers }: DriversPerformanceCardProps) => {
  const { selectedDriver, handleDriverChange } = useDriversPerformanceCard({ drivers });

  const hasDrivers = drivers.length > 0;

  return (
    <DriversStyledCard>
      <CardContent>
        <Typography variant="h5" gutterBottom mb={4}>
          Drivers Performance
        </Typography>

        {/* Driver Select Dropdown */}
        <FormControl fullWidth size="small" sx={{ mb: 3 }} disabled={!hasDrivers}>
          <InputLabel>Select Driver</InputLabel>
          <Select
            value={selectedDriver?.driverId || ''}
            label="Select Driver"
            onChange={(e) => handleDriverChange(Number(e.target.value))}
          >
            {!hasDrivers && (
              <MenuItem value="" disabled>
                No drivers available
              </MenuItem>
            )}
            {drivers.map((driver) => (
              <MenuItem key={driver.driverId} value={driver.driverId}>
                {driver.driverName}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        {/* Driver Stats */}
        {selectedDriver ? (
          <Box display="flex" flexDirection="column" gap={3}>
            {/* Total Transports */}
            <DriverStatItem>
              <LocalShippingIcon color="primary" />
              <DriverStatContent>
                <Typography variant="body2" color="text.secondary">
                  Total Transports
                </Typography>
                <Typography variant="h6">{selectedDriver.totalTransports}</Typography>
              </DriverStatContent>
            </DriverStatItem>

            {/* Total Revenue */}
            <DriverStatItem>
              <AttachMoneyIcon color="success" />
              <DriverStatContent>
                <Typography variant="body2" color="text.secondary">
                  Total Revenue
                </Typography>
                <Typography variant="h6">{selectedDriver.totalRevenue.toFixed(2)} BGN</Typography>
              </DriverStatContent>
            </DriverStatItem>

            {/* Average Revenue per Transport */}
            <DriverStatItem>
              <TrendingUpIcon color="info" />
              <DriverStatContent>
                <Typography variant="body2" color="text.secondary">
                  Avg Revenue per Transport
                </Typography>
                <Typography variant="h6">{selectedDriver.averageRevenuePerTransport.toFixed(2)} BGN</Typography>
              </DriverStatContent>
            </DriverStatItem>
          </Box>
        ) : (
          <Typography variant="body2" color="text.secondary">
            No data available
          </Typography>
        )}
      </CardContent>
    </DriversStyledCard>
  );
};
