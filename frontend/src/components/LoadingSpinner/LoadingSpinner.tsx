import { Box, CircularProgress } from '@mui/material';

import loadingSpinnerStyles from './LoadingSpinner.styles';

const LoadingSpinner = () => {
    return (
        <Box sx={loadingSpinnerStyles}>
            <CircularProgress />
        </Box>
    );
};

export default LoadingSpinner;