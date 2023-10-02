import React, { useEffect, useState } from 'react'
import { Box, Container, FormControl, InputLabel,  Select, Stack, useTheme } from '@mui/material'
import { useDispatch, useSelector } from 'react-redux';
import { jobLoadAction } from '../../../redux/actions/jobAction';
import AdminJobCard from '../../../component/AdminJobCard';
import MenuItem from "@mui/material/MenuItem";
import LoadingBox from '../../../component/LoadingBox';

const DashJobs = () => {
    const { jobs, loading } = useSelector(state => state.loadJobs);
    const { token } = useSelector( state => state.signIn);
    const {palette} = useTheme();
    const dispatch = useDispatch();
    const [filter, setFilter] = useState('');
 
    const handleStatusFilter = (e) => {
        setFilter(e.target.value);
    }

    useEffect(() => {
        if ( token === undefined )
            window.location.reload(true);
        dispatch(jobLoadAction());
    }, []);
    
    if (loading) {
        return <LoadingBox/> 
        ; 
      }

    return(
        <>
                <Container>
                    <Box>
                    <FormControl fullWidth>
                        <InputLabel id="demo-simple-select-label" sx={{ color: 'white'  }}>Filtrele</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={filter}
                            label="filter"
                            onChange={handleStatusFilter}
                            sx={{ color: 'white', backgroundColor: palette.secondary.light }}
                        >
                            <MenuItem value="">Hepsi</MenuItem>                                                
                            <MenuItem value={"ACTIVE"}>Aktif</MenuItem>
                            <MenuItem value={"PASSIVE"}>Pasif</MenuItem>
                            <MenuItem value={"CLOSED"}>Kapalı</MenuItem>
                        </Select>
                    </FormControl>
                    </Box>
                    <Stack
                        direction={{ xs: 'column', sm: 'row'}}
                        spacing={{ xs: 1, sm: 2, md: 4}}
                    >
                        <Box sx={{ flex: 5, p: 2 }}>
                            {   filter ? 
                                jobs.map((job) => (
                                    (job.status === filter)  &&
                                    <AdminJobCard id={job.id} status={job.status} code={job.code} jobTitle={job.title} description={job.description} qualifications={job.qualifications}></AdminJobCard>
                                    ))
                                    :
                                jobs.map((job) => (
                                <AdminJobCard id={job.id} status={job.status} code={job.code} jobTitle={job.title} description={job.description} qualifications={job.qualifications}></AdminJobCard>
                                ))
                            }
                            <Stack spacing={2}>
                            </Stack>
                        </Box>
                    </Stack>

                </Container>
        </>
    )
}

export default DashJobs;