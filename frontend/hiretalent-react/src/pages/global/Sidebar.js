import { Sidebar, Menu, MenuItem, menuClasses, useProSidebar } from 'react-pro-sidebar';
import GroupAddIcon from '@mui/icons-material/GroupAdd';
import { Box,  useTheme } from '@mui/material';
import WorkIcon from '@mui/icons-material/Work';
import WorkHistoryIcon from '@mui/icons-material/WorkHistory';
import Person3Icon from '@mui/icons-material/Person3';
import Avatar from '@mui/material/Avatar';
import logo from '../../assets/obsslogo.png';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { userLogoutAction} from '../../redux/actions/userAction';
import { useNavigate } from 'react-router-dom';
import LoginIcon from '@mui/icons-material/Login';
import HomeIcon from '@mui/icons-material/Home';
import AddIcon from '@mui/icons-material/Add';

const SidebarAdm = () => {
    const { isAuthenticated, role } = useSelector(state => state.signIn);
    const { palette } = useTheme();
    const { collapsed } = useProSidebar();
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const logOut = () => {
        if( role === "applicant" )
          window.location.replace("https://linkedin.com/m/logout");
        dispatch(userLogoutAction());
        setTimeout(() => {
          navigate('/'); 
        }, 500);
        window.location.reload(true);
      }


    return (
        <>
            <Sidebar backgroundColor="#003366" style={{ borderRightStyle: "none" }}>
                <Box sx={{ display: "flex", justifyContent: "space-between", flexDirection: "column", height: "100%" }}>
                    <Box>
                        <Box sx={{ pt: 3, pb: 5, display: "flex", justifyContent: "center" }}>

                            {
                                collapsed ?
                                    <Avatar alt="logo obss" 
                                     src={logo}
                                     /> :
                                    <Box sx={{ display: "flex", justifyContent: "center" }}>
                                        <img style={{ width: "200px", height: "75px", textAlign: "center", transition: "all ease-out .5s" }} src={logo} alt="logo obss" />
                                    </Box>
                            }

                        </Box>

                        <Menu

                            menuItemStyles={{


                                button: {
                                    [`&.${menuClasses.button}`]: {
                                        color: "#fafafa",
                                    },
                                    '&:hover': {
                                        backgroundColor: palette.secondary.dark,
                                        color: "#fafafa",
                                    },
                                },

                                icon: {
                                    [`&.${menuClasses.icon}`]: {
                                        // color: "blue",
                                        color: palette.primary.main,
                                    }
                                },
                            }}

                        >
                            {
                                isAuthenticated && role === "Hr" ?
                                    <>
                                        <MenuItem component={<Link to="/" />} icon={<HomeIcon />}> Anasayfa </MenuItem>
                                        <MenuItem component={<Link to="/admin/users" />} icon={<GroupAddIcon />}> Adaylar </MenuItem>
                                        <MenuItem component={<Link to="/admin/jobs" />} icon={<WorkIcon />}> İş İlanları </MenuItem>
                                        <MenuItem component={<Link to="/admin/add-job" />} icon={<AddIcon />}> İş İlanı Oluştur </MenuItem>
                                    </> :
                                    <>
                                        <MenuItem component={<Link to="/" />} icon={<HomeIcon />}> Anasayfa </MenuItem>
                                        <MenuItem component={<Link to="/user/jobs" />} icon={<WorkHistoryIcon />}> Başvurulan İş İlanları </MenuItem>
                                        <MenuItem component={<Link to="/user/info" />} icon={<Person3Icon />}> Hesap Bilgileri </MenuItem>
                                    </>
                            }

                        </Menu>
                    </Box>
                    <Box sx={{ pb: 2 }}>
                        <Menu
                            menuItemStyles={{


                                button: {
                                    [`&.${menuClasses.button}`]: {
                                        color: "#fafafa",
                                    },

                                    '&:hover': {
                                        backgroundColor: "rgba(23,105,170, 1)",
                                        color: "#fafafa",
                                    },
                                },

                                icon: {
                                    [`&.${menuClasses.icon}`]: {
                                        // color: "blue",
                                        color: palette.primary.main,
                                    }
                                },
                            }}
                        >
                            <MenuItem onClick={logOut} icon={<LoginIcon />}>Çıkış Yap</MenuItem>
                        </Menu>
                    </Box>
                </Box>
            </Sidebar>
        </>
    )
}

export default SidebarAdm