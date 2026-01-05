import { styled } from '@mui/material/styles';
import { Card, Box } from '@mui/material';

export const EmployeeStyledCard = styled(Card)({
  height: '450px',
  overflow: 'auto',
});

export const EmployeeDateRangeContainer = styled(Box)({
  display: 'flex',
  gap: '16px',
  marginBottom: '24px',
});

export const EmployeeStatItem = styled(Box)({
  display: 'flex',
  alignItems: 'center',
  gap: '8px',
});

export const EmployeeStatContent = styled(Box)({
  // Custom stat content styles if needed
});