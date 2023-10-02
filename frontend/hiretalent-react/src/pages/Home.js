import React, { useEffect, useRef } from "react";
import Navbar from "../component/Navbar";
import { Box, Container, Pagination, Stack } from "@mui/material";
import { useTheme } from "@emotion/react";
import { useDispatch, useSelector } from "react-redux";
import { loadActiveJobAction } from '../redux/actions/jobAction';
import JobCard from "../component/JobCard";
import Footer from "../component/Footer";
import LoadingBox from "../component/LoadingBox";
import { applicantUserSignInAction } from "../redux/actions/userAction";

const Home = () => {
    const { jobs, totalItems, loading } = useSelector(state => state.loadActiveJobs);
    const { isAuthenticated } = useSelector(state => state.signIn);
    const { palette } = useTheme();
    const dispatch = useDispatch();
    const [currentPage, setCurrentPage] = React.useState(1);
    const loggedInRef = useRef(false);
    const itemsPerPage = 5;
  
    function getAuthorizationCodeFromURL() {
        const urlParams = new URLSearchParams(window.location.search);
        console.log("kod:" + urlParams.get('code'));
        return urlParams.get('code');
    }

    const handlePageChange = (_event, newPage) => {
      setCurrentPage(newPage);
    };

    useEffect(() => {
        dispatch(loadActiveJobAction());
        let code = getAuthorizationCodeFromURL();
        if(loggedInRef.current  && code) return;
        loggedInRef.current = true;

        if(code && !isAuthenticated )
            dispatch(applicantUserSignInAction(code));
    }, []);

    if (!jobs) {
        return <div>Yükleniyor...</div>;
      }
      
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const currentData = jobs.slice(startIndex, endIndex);

    return(
        <>
            <Box sx={{
                bgcolor: palette.secondary.main, 
                minHeight: "100vh"
            }}>
                <Navbar/>
                <Container>
                    <Stack
                        direction={{ xs: 'column', sm: 'row'}}
                        spacing={{ xs: 1, sm: 2, md: 4}}
                    >
                        <Box sx={{ flex: 5, p: 2 }}>
                            {   
                                loading ? 
                                <LoadingBox/> :

                                currentData.map(
                                    (job) => (
                                <JobCard id={job.id} code={job.code} jobTitle={job.title} description={job.description} qualifications={job.qualifications}></JobCard>
                                )
                                )
                            }
                            <Stack spacing={2}>
                            <Pagination
                                count={Math.ceil(totalItems / itemsPerPage)}
                                page={currentPage}
                                onChange={handlePageChange}
                                color="primary"
                            />
                            </Stack>
                        </Box>
                    </Stack>
                </Container>
            </Box>
            <Footer/>
        </>
    )
}

export default Home;