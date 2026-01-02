import type { ReactNode } from 'react';

export interface Column<T> {
  id: keyof T | string;
  label: string;
  width?: string | number;
  align?: 'left' | 'center' | 'right';
  sortable?: boolean;
  render?: (row: T) => ReactNode;
}