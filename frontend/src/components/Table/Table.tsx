import type { ReactNode } from 'react';
import {
    Table as MuiTable,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    CircularProgress,
    Box,
    Typography,
    Tooltip,
} from '@mui/material';

import type { Column } from './Table.types';
import { tableStyles } from './Table.styles';

interface TableProps<T> {
    columns: Column<T>[];
    data: T[];
    isLoading?: boolean;
    onRowClick?: (row: T) => void;
    actions?: (row: T) => ReactNode;
    emptyMessage?: string;
    minWidth?: number;
    rowHeight?: number;
}

export const Table = <T extends { id: number | string }>({
    columns,
    data,
    isLoading = false,
    onRowClick,
    actions,
    emptyMessage = 'No data available',
    minWidth = 650,
    rowHeight = undefined,
}: TableProps<T>) => {
    const getCellContent = (row: T, column: Column<T>): string => {
        if (column.render) {
            return '';
        }
        return String(row[column.id as keyof T] ?? '-');
    };

    if (isLoading) {
        return (
            <Box sx={tableStyles.loadingContainer}>
                <CircularProgress />
            </Box>
        );
    }

    if (!data || data.length === 0) {
        return (
            <Paper sx={tableStyles.tableContainer}>
                <Box sx={tableStyles.emptyState}>
                    <Typography variant="h6" color="text.secondary">
                        {emptyMessage}
                    </Typography>
                </Box>
            </Paper>
        );
    }

    return (
        <TableContainer component={Paper} sx={tableStyles.tableContainer}>
            <MuiTable sx={{ minWidth }} aria-label="data table">
                <TableHead>
                    <TableRow>
                        {columns.map((column: Column<T>) => (
                            <TableCell
                                key={String(column.id)}
                                align={column.align || 'left'}
                                sx={{
                                    ...tableStyles.headerCell,
                                    width: column.width,
                                }}
                            >
                                {column.label}
                            </TableCell>
                        ))}
                        {actions && (
                            <TableCell
                                align="right"
                                sx={tableStyles.headerCell}
                            >
                                Actions
                            </TableCell>
                        )}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {data.map((row: T) => (
                        <TableRow
                            key={row.id}
                            sx={{
                                ...(onRowClick ? tableStyles.bodyRow : undefined),
                                height: rowHeight || 'auto',
                            }}
                            onClick={() => onRowClick?.(row)}
                        >
                            {columns.map((column: Column<T>) => {
                                const cellContent = getCellContent(row, column);
                                const renderedContent = column.render
                                    ? column.render(row)
                                    : cellContent;

                                const isTextColumn = !column.render ||
                                    (typeof renderedContent === 'string' ||
                                        typeof renderedContent === 'number');

                                return (
                                    <TableCell
                                        key={String(column.id)}
                                        align={column.align || 'left'}
                                        sx={isTextColumn ? tableStyles.bodyCell : undefined}
                                    >
                                        {isTextColumn ? (
                                            <Tooltip title={String(renderedContent)} arrow placement="top">
                                                <span>{renderedContent}</span>
                                            </Tooltip>
                                        ) : (
                                            renderedContent
                                        )}
                                    </TableCell>
                                );
                            })}
                            {actions && (
                                <TableCell
                                    align="right"
                                    sx={tableStyles.actionsCell}
                                >
                                    {actions(row)}
                                </TableCell>
                            )}
                        </TableRow>
                    ))}
                </TableBody>
            </MuiTable>
        </TableContainer>
    );
};

export default Table;