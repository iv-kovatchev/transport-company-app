import { CardContent, Typography, Grid } from '@mui/material';
import DirectionsCarIcon from '@mui/icons-material/DirectionsCar';
import LocalShippingIcon from '@mui/icons-material/LocalShipping';
import PeopleIcon from '@mui/icons-material/People';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CancelIcon from '@mui/icons-material/Cancel';
import { StyledCard, StatItem, StatContent } from './SummaryCard.styles';
import type { CompanySummaryReport } from '../../../types/report.types';

interface SummaryCardProps {
    summary: CompanySummaryReport;
}

export const SummaryCard = ({ summary }: SummaryCardProps) => {
    return (
        <StyledCard>
            <CardContent>
                <Typography variant="h5" gutterBottom mb={4}>
                    Company Summary
                </Typography>

                <Grid container spacing={3}>
                    {/* Total Transports */}
                    <Grid size={{ xs: 12, md: 6 }}>
                        <StatItem>
                            <LocalShippingIcon color="primary" />
                            <StatContent>
                                <Typography variant="body2" color="text.secondary">
                                    Total Transports
                                </Typography>
                                <Typography variant="h6">{summary.totalTransports}</Typography>
                            </StatContent>
                        </StatItem>
                    </Grid>

                    {/* Total Revenue */}
                    <Grid size={{ xs: 12, md: 6 }}>
                        <StatItem>
                            <AttachMoneyIcon color="success" />
                            <StatContent>
                                <Typography variant="body2" color="text.secondary">
                                    Total Revenue
                                </Typography>
                                <Typography variant="h6">{summary.totalRevenue.toFixed(2)} BGN</Typography>
                            </StatContent>
                        </StatItem>
                    </Grid>

                    {/* Total Vehicles */}
                    <Grid size={{ xs: 12, md: 6 }}>
                        <StatItem>
                            <DirectionsCarIcon color="info" />
                            <StatContent>
                                <Typography variant="body2" color="text.secondary">
                                    Total Vehicles
                                </Typography>
                                <Typography variant="h6">{summary.totalVehicles}</Typography>
                            </StatContent>
                        </StatItem>
                    </Grid>

                    {/* Total Employees */}
                    <Grid size={{ xs: 12, md: 6 }}>
                        <StatItem>
                            <PeopleIcon color="warning" />
                            <StatContent>
                                <Typography variant="body2" color="text.secondary">
                                    Total Employees
                                </Typography>
                                <Typography variant="h6">{summary.totalEmployees}</Typography>
                            </StatContent>
                        </StatItem>
                    </Grid>

                    {/* Paid Transports */}
                    <Grid size={{ xs: 12, md: 6 }}>
                        <StatItem>
                            <CheckCircleIcon color="success" />
                            <StatContent>
                                <Typography variant="body2" color="text.secondary">
                                    Paid Transports
                                </Typography>
                                <Typography variant="h6">{summary.paidTransports}</Typography>
                                <Typography variant="caption" color="text.secondary">
                                    {summary.paidRevenue.toFixed(2)} BGN
                                </Typography>
                            </StatContent>
                        </StatItem>
                    </Grid>

                    {/* Unpaid Transports */}
                    <Grid size={{ xs: 12, md: 6 }}>
                        <StatItem>
                            <CancelIcon color="error" />
                            <StatContent>
                                <Typography variant="body2" color="text.secondary">
                                    Unpaid Transports
                                </Typography>
                                <Typography variant="h6">{summary.unpaidTransports}</Typography>
                                <Typography variant="caption" color="text.secondary">
                                    {summary.unpaidRevenue.toFixed(2)} BGN
                                </Typography>
                            </StatContent>
                        </StatItem>
                    </Grid>
                </Grid>
            </CardContent>
        </StyledCard>
    );
};