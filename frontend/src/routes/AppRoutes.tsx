import { Routes, Route, Navigate } from 'react-router-dom';
import routes from './routeConfig';
import NotFound from '../pages/NotFound/NotFound';
import Layout from '../components/Layout/Layout';

const AppRoutes = () => {
  return (
    <Layout>
      <Routes>
        {/* All Routes (no authentication needed) */}
        {routes.map((route) => (
          <Route
            key={route.path}
            path={route.path}
            element={route.element}
          />
        ))}

        {/* Default Redirect */}
        <Route path="/" element={<Navigate to="/dashboard" replace />} />

        {/* 404 Page */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Layout>
  );
};

export default AppRoutes;