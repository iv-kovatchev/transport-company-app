const dialogStyles = {
    dialogTitle: {
        fontWeight: 600,
        fontSize: '1.25rem',
        display: 'flex',
        alignItems: 'center',
        gap: 1,
    },

    closeButton: {
        position: 'absolute',
        right: 8,
        top: 8,
        color: (theme: any) => theme.palette.grey[500],
    },

    dialogContent: {
        pt: 2,
    },

    contentContainer: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        gap: 2,
    },

    icon: {
        fontSize: 48,
    },

    description: {
        textAlign: 'center',
    },

    dialogActions: {
        px: 3,
        pb: 2,
    },
};

export default dialogStyles;