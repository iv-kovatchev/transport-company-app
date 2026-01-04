import { styled } from '@mui/material/styles';
import { Card, Box } from '@mui/material';

export const DriversStyledCard = styled(Card)({
  height: '400px',
  overflow: 'auto',
});

export const DriverStatItem = styled(Box)({
  display: 'flex',
  gap: '8px',
});

export const DriverStatContent = styled(Box)({
  // Custom stat content styles if needed
});