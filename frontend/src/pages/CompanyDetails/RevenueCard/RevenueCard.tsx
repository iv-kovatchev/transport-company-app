import { CardContent, Typography, Grid, TextField, Button, Snackbar, Alert } from '@mui/material';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CancelIcon from '@mui/icons-material/Cancel';
import LocalShippingIcon from '@mui/icons-material/LocalShipping';
import { 
  RevenueStyledCard, 
  DateRangeContainer, 
  RevenueStatItem, 
  RevenueStatContent 
} from './RevenueCard.styles';
import { useRevenueCard } from './useRevenueCard';

interface RevenueCardProps {
  companyId: number;
}

export const RevenueCard = ({ companyId }: RevenueCardProps) => {
  const {
    revenue,
    isLoading,
    startDate,
    endDate,
    snackbar,
    handleStartDateChange,
    handleEndDateChange,
    handleApply,
    handleCloseSnackbar,
  } = useRevenueCard({ companyId });

  if (isLoading) {
    return <Typography>Loading...</Typography>;
  }

  return (
    <>
      <RevenueStyledCard>
        <CardContent>
          <Typography variant="h5" gutterBottom mb={4}>
            Revenue by Period
          </Typography>

          {/* Date Range Inputs */}
          <DateRangeContainer>
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
          </DateRangeContainer>

          {revenue && (
            <Grid container spacing={3}>
              {/* Total Revenue */}
              <Grid size={{ xs: 12, md: 6 }}>
                <RevenueStatItem>
                  <AttachMoneyIcon color="primary" />
                  <RevenueStatContent>
                    <Typography variant="body2" color="text.secondary">
                      Total Revenue
                    </Typography>
                    <Typography variant="h6">{revenue.totalRevenue.toFixed(2)} BGN</Typography>
                  </RevenueStatContent>
                </RevenueStatItem>
              </Grid>

              {/* Paid Revenue */}
              <Grid size={{ xs: 12, md: 6 }}>
                <RevenueStatItem>
                  <CheckCircleIcon color="success" />
                  <RevenueStatContent>
                    <Typography variant="body2" color="text.secondary">
                      Paid Revenue
                    </Typography>
                    <Typography variant="h6">{revenue.paidRevenue.toFixed(2)} BGN</Typography>
                  </RevenueStatContent>
                </RevenueStatItem>
              </Grid>

              {/* Unpaid Revenue */}
              <Grid size={{ xs: 12, md: 6 }}>
                <RevenueStatItem>
                  <CancelIcon color="error" />
                  <RevenueStatContent>
                    <Typography variant="body2" color="text.secondary">
                      Unpaid Revenue
                    </Typography>
                    <Typography variant="h6">{revenue.unpaidRevenue.toFixed(2)} BGN</Typography>
                  </RevenueStatContent>
                </RevenueStatItem>
              </Grid>

              {/* Transports Count */}
              <Grid size={{ xs: 12, md: 6 }}>
                <RevenueStatItem>
                  <LocalShippingIcon color="info" />
                  <RevenueStatContent>
                    <Typography variant="body2" color="text.secondary">
                      Transports Count
                    </Typography>
                    <Typography variant="h6">{revenue.transportsCount}</Typography>
                  </RevenueStatContent>
                </RevenueStatItem>
              </Grid>
            </Grid>
          )}
        </CardContent>
      </RevenueStyledCard>

      {/* Snackbar for errors */}
      <Snackbar
        open={snackbar.open}
        autoHideDuration={4000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'left' }}
      >
        <Alert onClose={handleCloseSnackbar} severity={snackbar.severity} sx={{ width: '100%' }}>
          {snackbar.message}
        </Alert>
      </Snackbar>
    </>
  );
};