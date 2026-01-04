import { Typography, CircularProgress, Box } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { Container, Header, BackButton, LoadingContainer } from './CompanyDetails.styles';
import { useCompanyDetails } from './useCompanyDetails';
import { RevenueCard } from './RevenueCard/RevenueCard';
import { SummaryCard } from './SummaryCard/SummaryCard';
import { DriversPerformanceCard } from './DriversPerformanceCard/DriversPerformanceCard';

const CompanyDetails = () => {
  const {
    company,
    summary,
    drivers,
    isLoading,
    handleBackToDashboard,
  } = useCompanyDetails();


  if (isLoading) {
    return (
      <LoadingContainer>
        <CircularProgress />
      </LoadingContainer>
    );
  }

  if (!company) {
    return <Typography>Company not found</Typography>;
  }

  return (
    <Container>
      <Header>
        <BackButton
          startIcon={<ArrowBackIcon />}
          onClick={handleBackToDashboard}
          variant="outlined"
        >
          Back to Dashboard
        </BackButton>
        <Typography variant="h4">{company.name}</Typography>
      </Header>

      {/* Cards */}
      <Box display="flex" gap={3} mb={3}>
        {summary && (
          <Box flex={1}>
            <SummaryCard summary={summary} />
          </Box>
        )}
        <Box flex={1}>
          <RevenueCard companyId={company.id} />
        </Box>

        <Box flex={1}>
          <DriversPerformanceCard drivers={drivers || []} />
        </Box>
      </Box>
    </Container>
  );
};

export default CompanyDetails;