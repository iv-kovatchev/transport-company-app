import { Card, CardContent, Typography, Box } from '@mui/material';
import type { ReactNode } from 'react';
import statsCardStyles from './StatsCard.styles';

interface StatsCardProps {
  title: string;
  stats: {
    label: string;
    value: string | number;
    icon?: ReactNode;
  }[];
}

export const StatsCard = ({ title, stats }: StatsCardProps) => {
  return (
    <Card sx={statsCardStyles.card}>
      <CardContent>
        <Typography variant="h6" sx={statsCardStyles.title}>
          {title}
        </Typography>
        
        <Box sx={statsCardStyles.statsContainer}>
          {stats.map((stat, index) => (
            <Box key={index} sx={statsCardStyles.statRow}>
              {stat.icon && <Box>{stat.icon}</Box>}
              <Typography sx={statsCardStyles.statLabel}>
                {stat.label}: <Box component="span" sx={statsCardStyles.statValue}>{stat.value}</Box>
              </Typography>
            </Box>
          ))}
        </Box>
      </CardContent>
    </Card>
  );
};

export default StatsCard;