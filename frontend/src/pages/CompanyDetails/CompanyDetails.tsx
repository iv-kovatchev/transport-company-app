import { useParams } from 'react-router-dom';
import { Box, Typography, CircularProgress } from '@mui/material';
import { useGetCompany } from '../../api/companies/useGetCompany';

const CompanyDetails = () => {
  const { id } = useParams<{ id: string }>();
  const { data: company, isLoading } = useGetCompany(Number(id));

  if (isLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <CircularProgress />
      </Box>
    );
  }

  if (!company) {
    return <Typography>Company not found</Typography>;
  }

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        {company.name}
      </Typography>
      <Typography>Registration: {company.registrationNumber}</Typography>
      <Typography>Address: {company.address}</Typography>
      <Typography>Phone: {company.phone}</Typography>
      <Typography>Email: {company.email}</Typography>
    </Box>
  );
};

export default CompanyDetails;