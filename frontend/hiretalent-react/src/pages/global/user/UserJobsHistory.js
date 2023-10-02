import { Typography, Box } from '@mui/material'
import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getApplicationsAction } from '../../../redux/actions/userAction'
import AppliedJobCard from '../../../component/AppliedJobsCard'
import LoadingBox from '../../../component/LoadingBox'

const UserJobsHistory = () => {
    const { token, userInfo } = useSelector(state => state.signIn);
    const { applications, loading } = useSelector(state => state.loadApplicantApplications)
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(getApplicationsAction(token, userInfo))
    }, [dispatch, userInfo])

    console.log(applications)
    return (
        <>
            <Box>
                <Typography variant="h4" sx={{ color: "#fafafa" }}>Başvurulan İş İlanları</Typography>
                <Box>
                    {   !loading && applications ?
                        applications.map((job) => (
                            <AppliedJobCard application={job}></AppliedJobCard>
                        )):
                        <LoadingBox></LoadingBox>
                    }
                    { (applications === undefined || applications.length === 0 ) &&
                        <Typography variant="h5" sx={{ color: "#fafafa" , mt:3}}>
                            <i>Görünüşe göre henüz hiçbir işe başvurmadınız!</i>
                        </Typography>
                    }
                </Box>
            </Box>
        </>
    )
}

export default UserJobsHistory;