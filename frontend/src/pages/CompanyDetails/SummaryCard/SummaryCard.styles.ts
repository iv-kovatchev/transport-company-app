import { styled } from '@mui/material/styles';
import { Card, Box } from '@mui/material';

export const StyledCard = styled(Card)({
    height: '400px',
    overflow: 'auto',
});

export const StatItem = styled(Box)({
    display: 'flex',
    gap: '8px',
});

export const StatContent = styled(Box)({
    // Custom stat content styles if needed
});