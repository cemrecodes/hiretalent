import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { Collapse, Container, FormLabel, TextField, useTheme, Grid, Box} from '@mui/material';
import WorkIcon from '@mui/icons-material/Work';
import { Link } from 'react-router-dom';
import AddIcon from '@mui/icons-material/Add';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { changeJobStatusAction, closeJobPostingAction } from '../redux/actions/adminAction';
import { useCookies } from 'react-cookie';

const AdminJobCard = ({ status, jobTitle, code, description, id }) => {
    const { palette } = useTheme();
    const [expand, setExpand] = useState();
    const [date, setDate] = useState(null);
    const { token } = useSelector(state => state.signIn);
    const dispatch = useDispatch();
    const [cookies] = useCookies(['XSRF-TOKEN']);

    let statusText = '';

    if (status === 'ACTIVE') {
      statusText = 'AKTİF';
    } else if (status === 'PASSIVE') {
      statusText = 'PASİF';
    } else if (status === 'CLOSED') {
      statusText = 'KAPANDI';
    }

    const toggleStatus = () => {
        if (status === "ACTIVE") {
          return "PASİFLEŞTİR";
        } else {
          return "AKTİFLEŞTİR";
        }
    };  
        
    const handleExpandClick = () => {
        setExpand(!expand);
        setDate('');
    };

    const handleCloseClick = () => {
        console.log(token)
        dispatch(closeJobPostingAction(token, id, cookies['XSRF-TOKEN']));
    }

    const handleChangeStatusClick = (newStatus) => {
        if (date) {
            dispatch(changeJobStatusAction(token, id, newStatus, new Date(date).toISOString(), cookies['XSRF-TOKEN']));
            window.location.reload(true);
        } else {
            dispatch(changeJobStatusAction(token, id, newStatus, date, cookies['XSRF-TOKEN']));
            window.location.reload(true);
        }
    };
    
    const handleDateChange = (event) => {
        setDate(event.target.value);
    };
        
    return (
        <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>
            <Grid container >
                <Grid item xs={12} sm={6}>
                    <Box sx={{ p: 2 }}>
                        <CardContent >
                            <Typography  sx={{ fontSize: 15, color: palette.secondary.main, fontWeight: 500 }} gutterBottom>
                                <WorkIcon sx={{ color: palette.secondary.main, fontSize: 18 }} /> {statusText}
                            </Typography>
                            <Typography variant="h5" component="div">
                                {jobTitle}
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
                <Grid item xs={12} sm={6}>
                    <Box sx={{ p:2, display: 'flex', justifyContent: 'flex-end' }}>         
                        <CardActions>
                            { status === "CLOSED" ?
                            <>
                            <Button disableElevation variant='contained' size="large" startIcon={<AddIcon />}><Link style={{ textDecoration: "none", color: "white", boxShadow: 0 }} to={`/job/${id}`}>İLANA GİT</Link></Button>
                            <Button disableElevation variant='contained' size="large" startIcon={<AddIcon />}><Link style={{ textDecoration: "none", color: "white", boxShadow: 0 }} to={`/admin/job/application/${id}`}>BAŞVURULARI GÖR</Link></Button>
                            </>
                            :
                            <>
                            <Button disableElevation variant='contained' size="small" startIcon={<AddIcon />}><Link style={{ textDecoration: "none", color: "white", boxShadow: 0 }} to={`/job/${id}`}>İLANA GİT</Link></Button>
                            <Button disableElevation variant='contained' size="small" startIcon={<AddIcon />}><Link style={{ textDecoration: "none", color: "white", boxShadow: 0 }} to={`/admin/job/application/${id}`}>BAŞVURULARI GÖR</Link></Button>
                            </>
                            }   
                            { status !== "CLOSED" &&
                            <Button disableElevation variant='contained' size="small" onClick={handleCloseClick}>İLANI KAPAT</Button>
                            }
                            { status !== "CLOSED" &&
                            <Button disableElevation variant='contained' size="small" onClick={handleExpandClick}>İLANI {toggleStatus()}</Button>
                            }
                        </CardActions>
                    </Box>
                </Grid>
            </Grid>
            <CardContent>
                <Collapse in={expand}>
                    <Container fixed>
                        { status === "PASSIVE" ?
                            <>
                            <FormLabel>(İsteğe Bağlı) İleri Tarihte Aktifleştir</FormLabel>
                            <TextField
                            variant="outlined"
                            fullWidth
                            type="date"
                            id="dateActivated"
                            name="dateActivated"
                            value={date}
                            onChange={handleDateChange}
                            InputProps={{
                                inputProps: { min: new Date().toISOString().split('T')[0] },
                            }}
                            sx= {{ mb: 0.5}}
                            />
                            <Button type="submit" variant="contained" color="primary" onClick={()=>handleChangeStatusClick("ACTIVE")}>
                                AKTİFLEŞTİR
                            </Button>
                            </>
                            :
                            <>
                            <FormLabel>(İsteğe Bağlı) İleri Tarihte  Pasifleştir</FormLabel>
                            <TextField
                            variant="outlined"
                            fullWidth
                            type="date"
                            id="dateDeactivated"
                            name="dateDeactivated"
                            value={date}
                            onChange={handleDateChange}
                            InputProps={{
                                inputProps: { min: new Date().toISOString().split('T')[0] },
                            }}
                            sx= {{ mb: 0.5}}
                            />
                            <Button type="submit" variant="contained" color="primary" onClick={()=>handleChangeStatusClick("PASSIVE")}>
                                PASİFLEŞTİR
                            </Button>
                            </>                        
                        }
                    </Container>
                </Collapse>
            </CardContent>
        </Card>
    );
}

export default AdminJobCard;