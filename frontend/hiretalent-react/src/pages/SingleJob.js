import { Card, CardContent, Stack, Typography } from '@mui/material'
import { Box, Container } from '@mui/system'
import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Link, useParams } from 'react-router-dom'
import Footer from '../component/Footer'
import LoadingBox from '../component/LoadingBox'
import Navbar from '../component/Navbar'
import { jobLoadSingleAction } from '../redux/actions/jobAction'
import Button from '@mui/material/Button'
import { applicantLinkControlAction, userApplyJobAction } from '../redux/actions/userAction'
import { useCookies } from 'react-cookie'

const SingleJob = () => {
    const dispatch = useDispatch();
    const { singleJob, loading } = useSelector(state => state.singleJob);
    const { userInfo, role, token, isAuthenticated } = useSelector(state => state.signIn);
    const { link, error} = useSelector(state => state.applicantLinkControl);
    const { id } = useParams();
    const [cookies] = useCookies(['XSRF-TOKEN']);

    useEffect(() => {
        dispatch(jobLoadSingleAction(id));
        if ( token === undefined )
        window.location.reload(true);
    }, [id]);

    const applyForAJob = () => {
        if(isAuthenticated){
            dispatch(applicantLinkControlAction(token, userInfo))
        }
        console.log("link: ", link, "error: ", error)
        if(link)
            dispatch(userApplyJobAction(token, id , userInfo, cookies['XSRF-TOKEN']))
    };

    return (
        <>
            <Box sx={{ bgcolor: "#fafafa" }}>

                <Navbar />
                <Box sx={{ height: '85vh' }}>
                    <Container sx={{ pt: '30px' }}>
                        <Stack
                            direction={{ xs: 'column', sm: 'row' }}
                            spacing={{ xs: 1, sm: 2, md: 4 }}
                        >
                            <Box sx={{ flex: 4, p: 2 }}>
                                {
                                    loading ? <LoadingBox /> :
                                        <Card>
                                            <CardContent>
                                                <Typography variant="h5" component="h3">
                                                    {singleJob?.title}
                                                </Typography>
                                                <Typography variant="body2">
                                                    <Box component="span" sx={{ fontWeight: 700 }}>İlan kodu:</Box>{singleJob?.code}
                                                </Typography>
                                                <Typography variant="body2" sx={{ pt: 2 }}>
                                                    {singleJob?.description}
                                                </Typography>
                                                <Typography variant="body2" sx={{ pt: 2 }}>
                                                    <h3>Adaydan beklenen özellikler:</h3>
                                                    {singleJob?.qualifications}
                                                </Typography>
                                            </CardContent>
                                        </Card>
                                }
                            </Box>
                            { role !== "Hr" &&
                            <Box sx={{ flex: 1, p: 2 }}>
                                <Card sx={{ p: 2 }}>
                                    { isAuthenticated && role === "Applicant" ?
                                    <Button onClick={applyForAJob} sx={{ fontSize: "13px" }} variant='contained'>Bu İşe Başvur!</Button>
                                    :
                                    <Button sx={{ fontSize: "13px" }} variant='contained'>
                                        <Link style={{ textDecoration: "none", color: "white", boxShadow: 0 }} to={`/login`}>
                                            İşe Başvurmak İçin Giriş Yap!
                                        </Link>
                                    </Button>
                                    }
                                </Card>
                            </Box>
                            }
                        </Stack>
                    </Container>
                </Box>
                <Footer/>
            </Box>
        </>
    )
}

export default SingleJob;