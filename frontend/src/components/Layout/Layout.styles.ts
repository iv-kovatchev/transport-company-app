import type { SxProps, Theme } from '@mui/material';

export const SIDEBAR_WIDTH = 240;

const layoutStyles = {
  container: {
    display: 'flex',
    minHeight: '100vh',
  } as SxProps<Theme>,

  mainContent: {
    flexGrow: 1,
    width: '100%',
    overflow: 'hidden',
    p: 3,
    backgroundColor: 'background.default',
    transition: 'margin-left 0.3s ease',
  },

  centeredContent: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    minHeight: '100vh',
    backgroundColor: 'background.default',
  } as SxProps<Theme>,
};

export default layoutStyles;