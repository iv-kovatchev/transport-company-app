import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  typography: {
    fontFamily: '"Play", sans-serif',
  },
  components: {
    MuiCssBaseline: {
      styleOverrides: `
        @font-face {
          font-family: 'Play';
          font-style: normal;
          font-weight: 400;
          src: url('/fonts/play-regular.ttf') format('truetype');
        }
        @font-face {
          font-family: 'Play';
          font-style: normal;
          font-weight: 700;
          src: url('/fonts/play-bold.ttf') format('truetype');
        }
      `,
    },
  },
});

export default theme;