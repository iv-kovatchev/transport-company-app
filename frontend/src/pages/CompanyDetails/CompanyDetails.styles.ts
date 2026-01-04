import { styled } from '@mui/material/styles';
import { Box, Button } from '@mui/material';

export const Container = styled(Box)({
  // Main container styles if needed
});

export const Header = styled(Box)({
  marginBottom: '32px',
  display: 'flex',
  flexDirection: 'column',
  gap: '16px',
});

export const BackButton = styled(Button)({
    width: 'fit-content',
});

export const LoadingContainer = styled(Box)({
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  minHeight: '400px',
});