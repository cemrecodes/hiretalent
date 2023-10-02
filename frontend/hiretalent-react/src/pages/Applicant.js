import Navbar from "../component/Navbar";
import { Box, Container, Stack } from "@mui/material";
import { useTheme } from "@emotion/react";
import { useDispatch, useSelector } from "react-redux";
import Footer from "../component/Footer";
import LoadingBox from "../component/LoadingBox";
import ApplicantCard from '../component/ApplicantCard';
import { useParams } from "react-router-dom";
import { loadApplicantApplicationsAction } from "../redux/actions/applicationAction";
import { useEffect } from "react";
import JobCard from "../component/JobCard";

const  Applicant = () => {
    const dispatch = useDispatch();
    const { applications, loading } = useSelector(state => state.loadApplicant);
    const { token } = useSelector(state => state.signIn);
    const { palette } = useTheme();
    const { id } = useParams();

    useEffect(() => {
        dispatch(loadApplicantApplicationsAction(token, id));
    }, []);

    return(
        <>
            <Navbar/>
            <Box sx={{
                bgcolor: palette.secondary.main, 
                minHeight: "100vh"
            }}>
                <Container>
                    <Stack
                        direction={{ xs: 'column', sm: 'row'}}
                        spacing={{ xs: 1, sm: 2, md: 4}}
                    >
                        <Box sx={{ flex: 5, p: 2 }}>
                            {   
                                loading && 
                                <LoadingBox/> 
                            }
                            {
                                loading === false &&
                                    <>
                                    <ApplicantCard applicant={applications[0].applicant}></ApplicantCard>
                                    {applications.map(
                                    (job) => (
                                    <JobCard id={job.jobPosting.id} code={job.jobPosting.code} jobTitle={job.jobPosting.title} description={job.jobPosting.description} qualifications={job.jobPosting.qualifications}></JobCard>
                                    )
                                    )
                                    }
                                    </>
                            }
                          
                        </Box>
                    </Stack>
                </Container>
            </Box>
            <Footer/>
        </>
    )
}

export default Applicant;