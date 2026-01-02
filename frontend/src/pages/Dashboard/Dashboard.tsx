import { Box, Typography, IconButton, Alert, Button } from '@mui/material';
import BusinessIcon from '@mui/icons-material/Business';
import PeopleIcon from '@mui/icons-material/People';
import LocalShippingIcon from '@mui/icons-material/LocalShipping';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import WarningIcon from '@mui/icons-material/Warning';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import DownloadIcon from '@mui/icons-material/Download';

import Table from '../../components/Table/Table';
import useDashboard from './useDashboard.tsx';
import LoadingSpinner from '../../components/LoadingSpinner/LoadingSpinner.tsx';
import StatsCard from './StatsCard/StatsCard.tsx';
import DashboardChart from './DashboardChart/DashboardChart.tsx';
import CreateEditCompanyModal from './CreateEditCompanyModal/CreateEditCompanyModal.tsx';
import { DeleteCompanyDialog } from './DeleteCompanyDialog/DeleteCompanyDialog.tsx';

const Dashboard = () => {
  const {
    companies,
    transports,
    vehicles,
    columns,
    isLoading,
    error,
    handleEdit,
    handleDelete,
    handleCreate,
    handleCloseModal,
    isModalOpen,
    stats,
    selectedCompany,
    companyToDelete,
    isDeleteDialogOpen,
    handleCloseDeleteDialog,
    handleExportCSV,
  } = useDashboard();

  if (isLoading) {
    return <LoadingSpinner />;
  }

  if (error) {
    return (
      <Box p={3}>
        <Alert severity="error">
          Failed to load companies. Please try again later.
        </Alert>
      </Box>
    );
  }

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Dashboard
      </Typography>

      {/* Stats Card */}
      <Box display="flex" gap={3} mb={4} flexDirection={{ xs: 'column', md: 'row' }}>
        {/* Stats Card - 1/4 width */}
        <Box flex={{ xs: 1, md: '0 0 30%' }}>
          <StatsCard
            title="System Overview"
            stats={[
              {
                label: 'Total Companies',
                value: stats.totalCompanies,
                icon: <BusinessIcon color="primary" />
              },
              {
                label: 'Total Clients',
                value: stats.totalClients,
                icon: <PeopleIcon color="primary" />
              },
              {
                label: 'Total Employees',
                value: stats.totalEmployees,
                icon: <PeopleIcon color="primary" />
              },
              {
                label: 'Total Transports',
                value: stats.totalTransports,
                icon: <LocalShippingIcon color="primary" />
              },
              {
                label: 'Total Revenue',
                value: `${stats.totalRevenue.toFixed(2)} BGN`,
                icon: <AttachMoneyIcon color="success" />
              },
              {
                label: 'Pending Payments',
                value: `${stats.pendingPayments} (${stats.pendingAmount.toFixed(2)} BGN)`,
                icon: <WarningIcon color="warning" />
              },
            ]}
          />
        </Box>
        {/* Chart - 3/4 width */}
        <Box flex={{ xs: 1, md: '0 0 70%' }}>
          <DashboardChart transports={transports || []} vehicles={vehicles || []} />
        </Box>
      </Box>

      {/* Companies Table Section */}
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
        <Typography variant="h5">
          Companies
        </Typography>

        <Box display="flex" gap={2}>
          <Button
            variant="outlined"
            startIcon={<DownloadIcon />}
            onClick={handleExportCSV}
          >
            Export CSV
          </Button>

          <Button
            variant="contained"
            startIcon={<AddIcon />}
            onClick={handleCreate}
          >
            Create Company
          </Button>
        </Box>
      </Box>

      <Table
        columns={columns}
        data={companies}
        isLoading={isLoading}
        actions={(row) => (
          <>
            <IconButton size="small" onClick={() => handleEdit(row.id)} color="primary">
              <EditIcon />
            </IconButton>
            <IconButton size="small" onClick={() => handleDelete(row.id)} color="error">
              <DeleteIcon />
            </IconButton>
          </>
        )}
      />

      <CreateEditCompanyModal
        open={isModalOpen}
        onClose={handleCloseModal}
        company={selectedCompany}
      />

      <DeleteCompanyDialog
        open={isDeleteDialogOpen}
        onClose={handleCloseDeleteDialog}
        company={companyToDelete}
      />
    </Box>
  );
};

export default Dashboard;