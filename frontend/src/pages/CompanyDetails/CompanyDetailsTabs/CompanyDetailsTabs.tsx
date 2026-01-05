import { Box, Tabs, Tab, Button } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import type { EmployeeResponse } from '../../../types/employee.types';
import type { VehicleResponse } from '../../../types/vehicle.types';
import type { ClientResponse } from '../../../types/client.types';
import type { TransportResponse } from '../../../types/transport.types';
import { useCompanyDetailsTabs } from './useCompanyDetailsTabs';
import Table from '../../../components/Table/Table';
import { CreateEditEmployeeModal } from '../CreateEditEmployeeModal/CreateEditEmployeeModal';
import { DeleteEmployeeDialog } from '../DeleteEmployeeDialog/DeleteEmployeeDialog';
import { CreateEditVehicleModal } from '../CreateEditVehicleModal/CreateEditVehicleModal';
import { DeleteVehicleDialog } from '../DeleteVehicleDialog/DeleteVehicleDialog';

interface CompanyDetailsTabsProps {
  companyId: number;
  employees: EmployeeResponse[];
  vehicles: VehicleResponse[];
  clients: ClientResponse[];
  transports: TransportResponse[];
}

export const CompanyDetailsTabs = ({
  companyId,
  employees,
  vehicles,
  clients,
  transports,
}: CompanyDetailsTabsProps) => {
  const {
    activeTab,
    handleTabChange,
    employeeColumns,
    vehicleColumns,
    clientColumns,
    transportColumns,
    isEmployeeModalOpen,
    selectedEmployee,
    isEmployeeDeleteDialogOpen,
    employeeToDelete,
    handleCreateEmployee,
    handleCloseEmployeeModal,
    handleCloseEmployeeDeleteDialog,
    getEmployeeActions,
    isVehicleModalOpen,
    selectedVehicle,
    isVehicleDeleteDialogOpen,
    vehicleToDelete,
    handleCreateVehicle,
    handleCloseVehicleModal,
    handleCloseVehicleDeleteDialog,
    getVehicleActions,
  } = useCompanyDetailsTabs();

  return (
    <>
      <Box mt={6}>
        <Tabs value={activeTab} onChange={handleTabChange}>
          <Tab label="Employees" />
          <Tab label="Vehicles" />
          <Tab label="Clients" />
          <Tab label="Transports" />
        </Tabs>

        <Box mt={3}>
          {activeTab === 0 && (
            <>
              <Box display="flex" justifyContent="flex-end" mb={2}>
                <Button
                  variant="contained"
                  startIcon={<AddIcon />}
                  onClick={handleCreateEmployee}
                >
                  Create Employee
                </Button>
              </Box>
              <Table
                columns={employeeColumns}
                data={employees}
                emptyMessage="No employees found"
                actions={getEmployeeActions(employees)}
              />
            </>
          )}
          {activeTab === 1 && (
            <>
              <Box display="flex" justifyContent="flex-end" mb={2}>
                <Button
                  variant="contained"
                  startIcon={<AddIcon />}
                  onClick={handleCreateVehicle}
                >
                  Create Vehicle
                </Button>
              </Box>
              <Table
                columns={vehicleColumns}
                data={vehicles}
                emptyMessage="No vehicles found"
                actions={getVehicleActions(vehicles)}
              />
            </>
          )}
          {activeTab === 2 && (
            <Table columns={clientColumns} data={clients} emptyMessage="No clients found" />
          )}
          {activeTab === 3 && (
            <Table columns={transportColumns} data={transports} emptyMessage="No transports found" />
          )}
        </Box>
      </Box>

      {/* Employee Modals */}
      <CreateEditEmployeeModal
        open={isEmployeeModalOpen}
        onClose={handleCloseEmployeeModal}
        employee={selectedEmployee}
        companyId={companyId}
      />

      <DeleteEmployeeDialog
        open={isEmployeeDeleteDialogOpen}
        onClose={handleCloseEmployeeDeleteDialog}
        employee={employeeToDelete}
      />

      {/* Vehicle Modals */}
      <CreateEditVehicleModal
        open={isVehicleModalOpen}
        onClose={handleCloseVehicleModal}
        vehicle={selectedVehicle}
        companyId={companyId}
      />

      <DeleteVehicleDialog
        open={isVehicleDeleteDialogOpen}
        onClose={handleCloseVehicleDeleteDialog}
        vehicle={vehicleToDelete}
      />
    </>
  );
};
