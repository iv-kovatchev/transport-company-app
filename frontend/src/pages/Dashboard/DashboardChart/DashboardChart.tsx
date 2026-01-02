import { Card, CardContent, Box, Select, MenuItem, Typography, FormControl, InputLabel } from '@mui/material';
import type { TransportResponse } from '../../../types/transport.types';
import type { VehicleResponse } from '../../../types/vehicle.types';
import useDashboardChart from './useDashboardChart';

type ChartType = 'transports' | 'revenue' | 'vehicles' | 'payments';

interface DashboardChartProps {
  transports: TransportResponse[];
  vehicles: VehicleResponse[];
}

export const DashboardChart = ({ transports, vehicles }: DashboardChartProps) => {
  const {
    chartType,
    setChartType,
    renderChart,
  } = useDashboardChart({ transports, vehicles });

  return (
    <Card sx={{ height: '100%' }}>
      <CardContent>
        <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
          <Typography variant="h6" fontWeight={600}>
            Statistics
          </Typography>

          <FormControl size="small" sx={{ minWidth: 200 }}>
            <InputLabel>Chart Type</InputLabel>
            <Select
              value={chartType}
              label="Chart Type"
              onChange={(e) => setChartType(e.target.value as ChartType)}
            >
              <MenuItem value="transports">Transports per Month</MenuItem>
              <MenuItem value="revenue">Revenue per Month</MenuItem>
              <MenuItem value="vehicles">By Vehicle Type</MenuItem>
              <MenuItem value="payments">Paid vs Unpaid</MenuItem>
            </Select>
          </FormControl>
        </Box>

        {renderChart()}
      </CardContent>
    </Card>
  );
};

export default DashboardChart;