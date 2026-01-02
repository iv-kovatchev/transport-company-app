export const tableStyles = {
    tableContainer: {
        boxShadow: 1,
        borderRadius: 1,
        overflowX: 'auto',
        overflowY: 'visible',
        maxWidth: '100%',
        width: '100%',
    },

    table: {
        minWidth: 650,
    },

    headerCell: {
        fontWeight: 600,
        backgroundColor: 'primary.main',
        color: 'primary.contrastText',
        whiteSpace: 'nowrap',
    },

    bodyRow: {
        '&:hover': {
            backgroundColor: 'action.hover',
            cursor: 'pointer',
        },
        '&:last-child td, &:last-child th': {
            border: 0,
        },
    },

    bodyCell: {
        maxWidth: 200,
        overflow: 'hidden',
        textOverflow: 'ellipsis',
        whiteSpace: 'nowrap',
    },

    emptyState: {
        textAlign: 'center',
        py: 8,
    },

    loadingContainer: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        minHeight: 400,
    },

    actionsCell: {
        whiteSpace: 'nowrap',
    },
};