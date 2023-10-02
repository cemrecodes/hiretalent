import Box from '@mui/material/Box';
import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { userProfileAction } from '../../../redux/actions/userAction';
import LoadingBox from '../../../component/LoadingBox';
import UserCard from '../../../component/UserCard';

const UserInfoDashboard = () => {
    const { userInfo, token } = useSelector(state => state.signIn);
    const { user } = useSelector(state => state.userProfile);
    const dispatch = useDispatch();
    
    useEffect(() => {
        if ( token === undefined )
            window.location.reload(true);
        dispatch(userProfileAction(token, userInfo));
    }, [dispatch, userInfo])  

    return (
        <>
            <Box sx={{ maxWidth: "50%", margin: "auto", pt: 10 }}>
                { user ?
                <UserCard user={user}></UserCard>:
                <LoadingBox></LoadingBox>
                }
            </Box>
        </>
    )
}

export default UserInfoDashboard
