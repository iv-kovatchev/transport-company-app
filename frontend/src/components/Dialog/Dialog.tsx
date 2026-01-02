// src/components/Dialog/Dialog.tsx

import {
    Dialog as MuiDialog,
    DialogTitle,
    DialogContent,
    DialogContentText,
    DialogActions,
    Button,
    IconButton,
    CircularProgress,
    Box,
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import WarningAmberIcon from '@mui/icons-material/WarningAmber';
import InfoIcon from '@mui/icons-material/Info';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import dialogStyles from './Dialog.styles';

interface DialogProps {
    open: boolean;
    onClose: () => void;
    onConfirm: () => void;
    title: string;
    description: string;
    confirmText?: string;
    cancelText?: string;
    isLoading?: boolean;
    severity?: 'warning' | 'error' | 'info';
    maxWidth?: 'xs' | 'sm' | 'md' | 'lg' | 'xl';
}

export const Dialog = ({
    open,
    onClose,
    onConfirm,
    title,
    description,
    confirmText = 'Confirm',
    cancelText = 'Cancel',
    isLoading = false,
    severity = 'warning',
    maxWidth = 'xs',
}: DialogProps) => {
    const getSeverityIcon = () => {
        switch (severity) {
            case 'warning':
                return <WarningAmberIcon sx={{ ...dialogStyles.icon, color: 'warning.main' }} />;
            case 'error':
                return <ErrorOutlineIcon sx={{ ...dialogStyles.icon, color: 'error.main' }} />;
            case 'info':
                return <InfoIcon sx={{ ...dialogStyles.icon, color: 'info.main' }} />;
            default:
                return null;
        }
    };

    const getSeverityColor = () => {
        switch (severity) {
            case 'warning':
                return 'warning';
            case 'error':
                return 'error';
            case 'info':
                return 'info';
            default:
                return 'primary';
        }
    };

    return (
        <MuiDialog
            open={open}
            onClose={isLoading ? undefined : onClose}
            maxWidth={maxWidth}
            fullWidth
        >
            <DialogTitle sx={dialogStyles.dialogTitle}>
                {title}
                <IconButton
                    aria-label="close"
                    onClick={onClose}
                    disabled={isLoading}
                    sx={dialogStyles.closeButton}
                >
                    <CloseIcon />
                </IconButton>
            </DialogTitle>

            <DialogContent sx={dialogStyles.dialogContent}>
                <Box sx={dialogStyles.contentContainer}>
                    {getSeverityIcon()}
                    <DialogContentText sx={dialogStyles.description}>
                        {description}
                    </DialogContentText>
                </Box>
            </DialogContent>

            <DialogActions sx={dialogStyles.dialogActions}>
                <Button
                    onClick={onClose}
                    disabled={isLoading}
                    variant="outlined"
                >
                    {cancelText}
                </Button>
                <Button
                    onClick={onConfirm}
                    disabled={isLoading}
                    variant="contained"
                    color={getSeverityColor()}
                    startIcon={isLoading ? <CircularProgress size={20} /> : undefined}
                >
                    {confirmText}
                </Button>
            </DialogActions>
        </MuiDialog>
    );
};

export default Dialog;