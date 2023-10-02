import { Avatar, Box, Divider, Typography, Grid } from '@mui/material';
import React, { useEffect } from 'react';
import Footer from '../component/Footer';
import Navbar from '../component/Navbar';
import LockClockOutlined from '@mui/icons-material/LockClockOutlined'
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { useFormik } from 'formik';
import * as yup from 'yup';
import { useDispatch, useSelector } from 'react-redux';
import { hrUserSignInAction } from '../redux/actions/userAction';
import { useNavigate } from 'react-router-dom';
import LinkedInIcon from '@mui/icons-material/LinkedIn';
import { useCookies } from 'react-cookie';

const validationSchema = yup.object({
    username: yup
        .string('Kullanıcı Adı')
        .required('Kullanıcı adı zorunludur!'),
    password: yup
        .string('Şifre')
        .required('Şifre zorunludur!'),
});

function handleClick() {
      window.location.replace("https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=${client_id}&redirect_uri=http%3A%2F%2Flocalhost%3A3000%2F&state=foobar&scope=r_liteprofile%20r_emailaddress")
}

const Login = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const { isAuthenticated, role} = useSelector(state => state.signIn);
    const [cookies] = useCookies(['XSRF-TOKEN']);
    
    useEffect(() => {
        if (isAuthenticated) {
          if (role === "Hr") {
            navigate('/admin/jobs');
          }
        }
      }, [isAuthenticated, role]);

    const formik = useFormik({
        initialValues: {
            username: '',
            password: ''
        },
        validationSchema: validationSchema,
        onSubmit: (values, actions) => {
            dispatch(hrUserSignInAction(values, cookies['XSRF-TOKEN']));
            actions.resetForm();
        }
    })

    return (
        <>
        <Navbar />
            <Box sx={{ mb: 3, mt: 3, height: '79vh', width: '100vh' ,display: "flex", alignItems: "center", justifyContent: "center"  }} className='form_style border-style'>
                <Grid item xs={6} sx={{m:3}} >
                    <Box sx={{ p: 2 }}>
                        <Box onSubmit={formik.handleSubmit} component="form" >
                            <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", width: "100%" }}>
                                <Avatar sx={{ m: 1, bgcolor: "primary.main", mb: 3 }}>
                                    <LockClockOutlined />
                                </Avatar>
                                <Box sx={{mb: 3}}>
                                    <Typography> İNSAN KAYNAKLARI GİRİŞİ </Typography>
                                </Box>
                                <TextField sx={{ mb: 3 }}
                                    fullWidth
                                    id="username"
                                    label="username"
                                    name='username'
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    placeholder="Username"
                                    value={formik.values.username}
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur}
                                    error={formik.touched.username && Boolean(formik.errors.username)}
                                    helperText={formik.touched.username && formik.errors.username}
                                />
                                <TextField sx={{ mb: 3 }}
                                    fullWidth
                                    id="password"
                                    name="password"
                                    label="Password"
                                    type="password"
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    placeholder="Password"
                                    value={formik.values.password}
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur}
                                    error={formik.touched.password && Boolean(formik.errors.password)}
                                    helperText={formik.touched.password && formik.errors.password}
                                />
                                <Button fullWidth variant="contained" type='submit'>GİRİŞ YAP</Button>                       
                            </Box>
                        </Box>
                    </Box>
                </Grid>
                <Divider orientation="vertical" flexItem />
                    <Grid item xs={6} sx={{m:3}} >
                        <Box sx={{ p:2, display: 'flex', justifyContent: 'flex-end' , flexDirection: "column", alignItems: "center", width: "100%" }}>
                            <LinkedInIcon sx={{ fontSize: 55, m: 1, color: "primary.main", mb: 3 }}/>
                            <Box sx={{mb:3}}>
                                <Typography> KULLANICI GİRİŞİ </Typography>
                            </Box>
                            <Button fullWidth variant="contained" onClick={handleClick}>LINKEDIN İLE GİRİŞ YAP/ÜYE OL</Button>
                        </Box>
                    </Grid>
            </Box>    
        <Footer/>
        </>
    )
}

export default Login;