import { Box } from '@mui/material';
import layoutStyles from './Layout.styles';
//import Sidebar from '../Sidebar/Sidebar';

interface LayoutProps {
  children: React.ReactNode;
}

const Layout = ({ children }: LayoutProps) => {
  return (
    <Box sx={layoutStyles.container}>
      {/* <Sidebar /> */}
      <Box component="main" sx={layoutStyles.mainContent}>
        {children}
      </Box>
    </Box>
  );
};

export default Layout;