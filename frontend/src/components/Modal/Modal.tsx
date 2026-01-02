import type { ReactNode } from 'react';

import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    IconButton,
    Box,
    CircularProgress,
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import modalStyles from './Modal.styles';

interface BaseModalProps {
    open: boolean;
    onClose: () => void;
    title: string;
    maxWidth?: 'xs' | 'sm' | 'md' | 'lg' | 'xl';
}

interface FormModalProps extends BaseModalProps {
    onSubmit: () => void;
    isLoading?: boolean;
    submitText?: string;
    cancelText?: string;
    children: ReactNode;
    hideDefaultActions?: boolean;
}

export const Modal = ({
    open,
    onClose,
    onSubmit,
    title,
    children,
    isLoading = false,
    submitText = 'Submit',
    cancelText = 'Cancel',
    maxWidth = 'sm',
    hideDefaultActions = false,
}: FormModalProps) => {
    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSubmit();
    };

    return (
        <Dialog
            open={open}
            onClose={onClose}
            maxWidth={maxWidth}
            fullWidth
        >
            <DialogTitle sx={modalStyles.dialogTitle}>
                {title}
                <IconButton
                    aria-label="close"
                    onClick={onClose}
                    sx={modalStyles.closeButton}
                >
                    <CloseIcon />
                </IconButton>
            </DialogTitle>

            <Box component="form" onSubmit={handleSubmit} noValidate>
                <DialogContent sx={modalStyles.dialogContent}>
                    {children}
                </DialogContent>

                {!hideDefaultActions &&
                    <DialogActions sx={modalStyles.dialogActions}>
                        <Button
                            onClick={onClose}
                            disabled={isLoading}
                        >
                            {cancelText}
                        </Button>
                        <Button
                            type="submit"
                            variant="contained"
                            disabled={isLoading}
                            startIcon={isLoading ? <CircularProgress size={20} /> : undefined}
                        >
                            {submitText}
                        </Button>
                    </DialogActions>
                }
            </Box>
        </Dialog>
    );
};

export default Modal;