import React, { useEffect, useState } from 'react'
import { Box, Button, Container, FormControl, InputBase, InputLabel, Pagination, Select, Stack, alpha, useTheme } from '@mui/material'
import { useDispatch, useSelector } from 'react-redux';
import { applicationLoadAction } from '../../../redux/actions/applicationAction';
import AdminApplicationCard from '../../../component/AdminApplicationCard';
import { useParams } from 'react-router-dom';
import SearchIcon from '@mui/icons-material/Search';
import styled from '@emotion/styled';
import { searchApplicantOnApplicationAction } from '../../../redux/actions/adminAction';
import LoadingBox from '../../../component/LoadingBox';
import DeleteIcon from '@mui/icons-material/Delete';
import MenuItem from "@mui/material/MenuItem";

const SearchContainer = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: "center",
    width: '100%', // Take the full width of the parent container
    [theme.breakpoints.up('sm')]: {
      width: 'auto', // Allow it to have its natural width on larger screens
    },
    color: "white"
  }));
  
  const Search = styled('div')(({ theme }) => ({
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: alpha(theme.palette.common.white, 0.15),
    '&:hover': {
      backgroundColor: alpha(theme.palette.common.white, 0.25),
    },
    flex: 0.5, 
  }));
  
  const SearchIconWrapper = styled('div')(({ theme }) => ({
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  }));
  
  const StyledInputBase = styled(InputBase)(({ theme }) => ({
    color: 'inherit',
    '& .MuiInputBase-input': {
      padding: theme.spacing(1, 1, 1, 0),
      paddingLeft: `calc(1em + ${theme.spacing(4)})`,
      width: '100%',
    },
  }));

const DashApplications = () => {
    const [ searched, setSearched] = useState(false);
    const [ text,setText ] = useState("");
    const { applications, totalItems } = useSelector(state => state.loadApplications);
    const { applicants, loading } = useSelector(state => state.searchOnApplications);
    const { token } = useSelector(state => state.signIn);
    const { palette } = useTheme();
    const dispatch = useDispatch();
    const [currentPage, setCurrentPage] = React.useState(1);
    const [filter, setFilter] = useState('');
    const { id } = useParams();

    const itemsPerPage = 5;

    const handlePageChange = (event, newPage) => {
      setCurrentPage(newPage);
    };

    useEffect(() => {
        dispatch(applicationLoadAction(token, id));
    }, []);
    
    const cleanText = () => {
      setText("");
      setSearched(false);
    };

    const handleChange = (value) => {
        setText(value);
    }

    const handleClick = () => {
        dispatch(searchApplicantOnApplicationAction(token, id, text));
        setText("");
        setSearched(!searched);
    };

    const handleStatusFilter = (e) => {
        setFilter(e.target.value);
    }

    if (!applications) {
        return <div>Loading...</div>; 
    }

    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const currentData = applications.slice(startIndex, endIndex);

    return(
        <>
            <SearchContainer>
                <Box sx={{ flex: 0.3, mr: 2 }}>
                    <FormControl fullWidth >
                        <InputLabel id="demo-simple-select-label" sx={{ color: 'white' }}>Filtrele</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={filter}
                            label="filter"
                            onChange={handleStatusFilter}
                            sx={{ color: 'white', backgroundColor: palette.secondary.light , height: 39 }}
                        >
                            <MenuItem value="">Hepsi</MenuItem>                                                
                            <MenuItem value={"ACCEPTED"}>Kabul Edildi</MenuItem>
                            <MenuItem value={"REJECTED"}>Reddedildi</MenuItem>
                            <MenuItem value={"EVALUATION"}>İşleme Alındı</MenuItem>
                        </Select>
                </FormControl>
                </Box>
                <Search>
                    <SearchIconWrapper>
                    <SearchIcon />
                    </SearchIconWrapper>
                    <StyledInputBase
                    placeholder="Kullanıcı Arat…"
                    value = {text}
                    onChange = { (i) => handleChange(i.target.value)}
                    />
                </Search>
                <Button disableElevation variant='contained' size="small" sx={{height: 39 , ml: '7px'}} onClick={handleClick}>
                    <SearchIcon/>
                </Button>
                <Button disableElevation variant='contained' size="small" sx={{height: 39 , ml: '7px'}} onClick={cleanText}>
                    <DeleteIcon/>
                </Button>
            </SearchContainer>
                <Container>
                    <Stack
                        direction={{ xs: 'column', sm: 'row'}}
                        spacing={{ xs: 1, sm: 2, md: 4}}
                    >
                        <Box sx={{ flex: 5, p: 2 }}>
                            {   !searched && filter ? 
                                currentData.map((application) => (
                                    (application.status === filter)  &&
                                <AdminApplicationCard id={application.id} applicant={application.applicant} dateCreated={application.dateCreated} status={application.status} ></AdminApplicationCard>
                                ))
                                
                                :

                                currentData.map((application) => (
                                <AdminApplicationCard id={application.id} applicant={application.applicant} dateCreated={application.dateCreated} status={application.status} ></AdminApplicationCard>
                                ))
                            }
                            { !searched &&
                            <Stack spacing={2}>
                            <Pagination
                                count={Math.ceil(totalItems / itemsPerPage)}
                                page={currentPage}
                                onChange={handlePageChange}
                                color="primary"
                            />
                            </Stack>
                            }
                            {
                                searched && (
                                    loading ?
                                    <LoadingBox/>
                                    :
                                    applicants.map(
                                        (applicant) => (
                                        <AdminApplicationCard id={applicant.id} applicant={applicant.applicant} dateCreated={applicant.dateCreated} status={applicant.status}></AdminApplicationCard>
                                    )
                                    )
                                )
                            }
                        </Box>
                    </Stack>

                </Container>
        </>
    )
}

export default DashApplications;