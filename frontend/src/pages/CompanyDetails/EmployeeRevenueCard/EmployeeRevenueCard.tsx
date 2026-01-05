import { CardContent, Typography, Select, MenuItem, FormControl, InputLabel, TextField, Button, Snackbar, Alert, Box } from '@mui/material';
import LocalShippingIcon from '@mui/icons-material/LocalShipping';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import TrendingUpIcon from '@mui/icons-material/TrendingUp';
import { 
  EmployeeStyledCard, 
  EmployeeDateRangeContainer, 
  EmployeeStatItem, 
  EmployeeStatContent 
} from './EmployeeRevenueCard.styles';
import { useEmployeeRevenueCard } from './useEmployeeRevenueCard';
import type { EmployeeResponse } from '../../../types/employee.types';

interface EmployeeRevenueCardProps {
  employees: EmployeeResponse[];
}

export const EmployeeRevenueCard = ({ employees }: EmployeeRevenueCardProps) => {
  const {
    employeeRevenue,
    isLoading,
    selectedEmployee,
    startDate,
    endDate,
    snackbar,
    handleEmployeeChange,
    handleStartDateChange,
    handleEndDateChange,
    handleApply,
    handleCloseSnackbar,
  } = useEmployeeRevenueCard({ employees });

  const hasEmployees = employees.length > 0;

  if (isLoading) {
    return <Typography>Loading...</Typography>;
  }

  return (
    <>
      <EmployeeStyledCard>
        <CardContent>
          <Typography variant="h5" gutterBottom mb={4}>
            Employee Revenue
          </Typography>

          {/* Employee Select Dropdown */}
          <FormControl fullWidth size="small" sx={{ mb: 3 }} disabled={!hasEmployees}>
            <InputLabel>Select Employee</InputLabel>
            <Select
              value={selectedEmployee?.id || ''}
              label="Select Employee"
              onChange={(e) => handleEmployeeChange(Number(e.target.value))}
            >
              {!hasEmployees && (
                <MenuItem value="" disabled>
                  No employees available
                </MenuItem>
              )}
              {employees.map((employee) => (
                <MenuItem key={employee.id} value={employee.id}>
                  {employee.firstName} {employee.lastName}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          {/* Date Range Inputs */}
          <EmployeeDateRangeContainer>
            <TextField
              label="Start Date"
              type="date"
              value={startDate}
              onChange={(e) => handleStartDateChange(e.target.value)}
              InputLabelProps={{ shrink: true }}
              size="small"
            />
            <TextField
              label="End Date"
              type="date"
              value={endDate}
              onChange={(e) => handleEndDateChange(e.target.value)}
              InputLabelProps={{ shrink: true }}
              size="small"
            />
            <Button variant="contained" onClick={handleApply}>
              Apply
            </Button>
          </EmployeeDateRangeContainer>

          {/* Employee Stats */}
          {employeeRevenue ? (
            <Box display="flex" flexDirection="column" gap={3}>
              {/* Total Transports */}
              <EmployeeStatItem>
                <LocalShippingIcon color="primary" />
                <EmployeeStatContent>
                  <Typography variant="body2" color="text.secondary">
                    Total Transports
                  </Typography>
                  <Typography variant="h6">{employeeRevenue.totalTransports}</Typography>
                </EmployeeStatContent>
              </EmployeeStatItem>

              {/* Total Revenue */}
              <EmployeeStatItem>
                <AttachMoneyIcon color="success" />
                <EmployeeStatContent>
                  <Typography variant="body2" color="text.secondary">
                    Total Revenue
                  </Typography>
                  <Typography variant="h6">{employeeRevenue.totalRevenue.toFixed(2)} BGN</Typography>
                </EmployeeStatContent>
              </EmployeeStatItem>

              {/* Average Revenue per Transport */}
              <EmployeeStatItem>
                <TrendingUpIcon color="info" />
                <EmployeeStatContent>
                  <Typography variant="body2" color="text.secondary">
                    Avg Revenue per Transport
                  </Typography>
                  <Typography variant="h6">{employeeRevenue.averageRevenuePerTransport.toFixed(2)} BGN</Typography>
                </EmployeeStatContent>
              </EmployeeStatItem>
            </Box>
          ) : (
            <Typography variant="body2" color="text.secondary">
              No data available
            </Typography>
          )}
        </CardContent>
      </EmployeeStyledCard>

      {/* Snackbar for errors */}
      <Snackbar
        open={snackbar.open}
        autoHideDuration={4000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
      >
        <Alert onClose={handleCloseSnackbar} severity={snackbar.severity} sx={{ width: '100%' }}>
          {snackbar.message}
        </Alert>
      </Snackbar>
    </>
  );
};