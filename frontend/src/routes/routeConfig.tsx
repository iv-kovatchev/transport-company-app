import type { JSX } from 'react';
import Dashboard from '../pages/Dashboard/Dashboard';
import CompanyDetails from '../pages/CompanyDetails/CompanyDetails';
//import Companies from '../pages/Companies/Companies';
//import Clients from '../pages/Clients/Clients';
// ... други pages

export interface Route {
  path: string;
  element: JSX.Element;
}

const routes: Route[] = [
  { path: '/dashboard', element: <Dashboard /> },
  { path: '/companies/:id', element: <CompanyDetails /> },
 // { path: '/companies', element: <Companies /> },
 // { path: '/clients', element: <Clients /> },
  // ... други routes
];

export default routes;