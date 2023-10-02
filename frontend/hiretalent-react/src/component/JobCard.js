import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { useTheme, Grid } from '@mui/material';
import { Link } from 'react-router-dom';
import AddIcon from '@mui/icons-material/Add';
import WorkIcon from '@mui/icons-material/Work';

const JobCard = ({ jobTitle, code, description, id }) => {
    const { palette } = useTheme();
    return (
        <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>
            <Grid container >
                <Grid item xs={6}>
                    <Box sx={{ p: 2 }}>
                        <CardContent >
                            <Typography variant="h5" component="div">
                            <WorkIcon sx={{ color: palette.secondary.main, fontSize: 22 }} />
                                {" " + jobTitle}
                            </Typography>
                            <Typography sx={{ mb: 1.5 }} color="text.secondary">
                                İlan Kodu: {code}
                            </Typography>
                            <Typography variant="body2">
                                {description.split(" ").slice(0, 15).join(" ") + "..."}
                            </Typography>
                        </CardContent>
                    </Box>
                </Grid>
                <Grid item xs={6} justifyContent="center">
                    <Box sx={{ p:2, display: 'flex', justifyContent: 'flex-end' }}>
                        <CardActions>
                            <Button disableElevation variant='contained' size="medium" startIcon={<AddIcon />}>
                                <Link style={{ textDecoration: "none", color: "white", boxShadow: 0 }} to={`/job/${id}`}>
                                    Detaylar
                                </Link>
                            </Button>
                        </CardActions>
                    </Box>
                </Grid>
            </Grid>
        </Card>
    );
}

export default JobCard;