import { styled } from '@mui/material/styles';
import { Card, Box } from '@mui/material';

export const RevenueStyledCard = styled(Card)({
    height: '450px',
    overflow: 'auto',
});

export const DateRangeContainer = styled(Box)({
  display: 'flex',
  gap: '16px',
  marginBottom: '24px',
});

export const RevenueStatItem = styled(Box)({
  display: 'flex',
  gap: '8px',
});

export const RevenueStatContent = styled(Box)({
  // Custom stat content styles if needed
});