import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { TextField, useTheme } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import { useDispatch, useSelector } from 'react-redux';
import { addApplicantLinkAction } from '../redux/actions/userAction';
import { useState } from 'react';
import { useCookies } from 'react-cookie';

const UserCard = ( user ) => {
    const { palette } = useTheme();
    const { userInfo, token } = useSelector(state => state.signIn);
    const dispatch = useDispatch();
    const [link, setLink] = useState(user.user.link);
    const [cookies] = useCookies(['XSRF-TOKEN']);

    const addLink = () =>
    {
        if(link)
            dispatch(addApplicantLinkAction(token, userInfo, link, cookies['XSRF-TOKEN']));
    }

    return (
                <Card sx={{ minWidth: 275, bgcolor: palette.secondary.light}}>
                    <CardContent>
                        <Typography variant="h5" color="#fafafa" gutterBottom>
                            Kişisel Bilgiler
                        </Typography>
                        <hr style={{ marginBottom: "30px" }} />
                        <Typography variant="h6" component="div" sx={{ color: "#fafafa" }} >
                          <b>Ad: </b> {user.user.name}
                        </Typography>
                        <Typography variant="h6" component="div" sx={{ color: "#fafafa" }} >
                           <b>Soyad: </b> {user.user.surname}
                        </Typography>
                        <Typography variant="h6" component="div" sx={{ color: "#fafafa" }} >
                           <b>E-mail:</b>  {user.user.email}
                        </Typography>
                        {   user.user.phoneNumber && 
                            <Typography variant="h6" component="div" sx={{ color: "#fafafa" }} >
                            <b>Telefon Numarası:</b>  {user.user.phoneNumber}
                            </Typography>
                        }
                        <Typography variant="h6" component="div" sx={{ color: "#fafafa" }} >
                           <b>Linkedin hesabı: </b>  <TextField sx={{ width: '70%' }} id="standard-basic" onChange={e => setLink(e.target.value)} label="Linkedin link" defaultValue={user.user.link} variant="standard" />
                        </Typography>
                    </CardContent>
                    { !user.user.link &&
                    <Box sx={{ flex: 1, p: 2 }}>
                        <Button onClick={addLink} sx={{ fontSize: "13px" }} variant='contained' startIcon={<AddIcon />}>Linkedin Linki Ekle</Button>  
                    </Box>
                    }
                </Card>
    );
}

export default UserCard;