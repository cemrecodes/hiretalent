import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import { Link, useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';
import logo from '../assets/obsslogo.png';
import { useDispatch, useSelector } from 'react-redux';
import { userLogoutAction } from '../redux/actions/userAction';
import { IconButton } from '@mui/material';

function Navbar() {
  const { isAuthenticated, role } = useSelector( state => state.signIn);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { palette } = useTheme();
  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const [anchorElUser, setAnchorElUser] = React.useState(null);
  
  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

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
    <AppBar position="static" sx={{ bgcolor: palette.primary.main }}>
    <Container >
        {/* principal Menu */}
        <Toolbar disableGutters>
            <Typography
                variant="h6"
                noWrap
                component="a"
                href="/"
                sx={{
                    mr: 2,
                    display: { xs: 'none', md: 'flex' },
                    fontFamily: 'monospace',
                    fontWeight: 700,
                    letterSpacing: '.3rem',
                    color: 'inherit',
                    textDecoration: 'none',
                }}
            >
                <img src={logo} alt="logo" height="auto" width="30%"></img>
            </Typography>

            <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
                <IconButton
                    size="large"
                    aria-label="account of current user"
                    aria-controls="menu-appbar"
                    aria-haspopup="true"
                    onClick={handleOpenNavMenu}
                    color="inherit"
                >
                    <MenuIcon />
                </IconButton>
                <Menu
                    id="menu-appbar"
                    anchorEl={anchorElNav}
                    anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'left',
                    }}
                    keepMounted
                    transformOrigin={{
                        vertical: 'top',
                        horizontal: 'left',
                    }}
                    open={Boolean(anchorElNav)}
                    onClose={handleCloseNavMenu}
                    sx={{
                        display: { xs: 'block', md: 'none' },
                    }}
                >
                <MenuItem onClick={handleCloseNavMenu}>
                    <Typography textAlign="center"><Link to="/" textAlign="center"  style={{color:"black ", textDecoration: "none" }} >ANASAYFA</Link></Typography>
                </MenuItem>
                { !isAuthenticated &&
                <MenuItem onClick={handleCloseNavMenu}>
                    <Typography textAlign="center"><Link to="/login" textAlign="center"  style={{color:"black ", textDecoration: "none" }}>Giriş Yap</Link></Typography>
                </MenuItem>
                }
                </Menu>
            </Box>
            <Typography
                variant="h6"
                noWrap
                component="a"
                href=""
                sx={{
                    mr: 2,
                    display: { xs: 'flex', md: 'none' },
                    flexGrow: 1,
                    fontFamily: 'monospace',
                    fontWeight: 700,
                    letterSpacing: '.2rem',
                    color: 'inherit',
                    textDecoration: 'none',
                }}
            >
                <img src={logo} alt="logo" height="auto" width="30%"></img>
            </Typography>
            <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
                <Button
                    onClick={handleCloseNavMenu}
                    sx={{ my: 2, color: 'white', display: 'block' }}>
                    <Link to="/" style={{ color: 'white', textDecoration: "none" }}>
                    <b> ANASAYFA </b> 
                    </Link>
                </Button>
                { !isAuthenticated &&
                <Button
                    onClick={handleCloseNavMenu}
                    sx={{ my: 2, color: 'white', display: 'block' }}>
                    <Link to="/login" style={{ color: 'white', textDecoration: "none" }}>
                    <b> GİRİŞ YAP </b> 
                    </Link>
                </Button>
                }

            </Box>

            { isAuthenticated && 
            <Box sx={{ flexGrow: 0 }}>
                <Tooltip title="Open settings">
                    <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                        <Avatar sx={{ color: palette.primary.white }} src="" />
                    </IconButton>
                </Tooltip>
                <Menu
                    PaperProps={{
                        sx: {
                            "& 	.MuiMenu-list": {
                                bgcolor: "primary.white",
                                color: "white"
                            },
                        }
                    }}

                    sx={{ mt: '45px' }}
                    id="menu-appbar"
                    anchorEl={anchorElUser}
                    anchorOrigin={{
                        vertical: 'top',
                        horizontal: 'right',
                    }}
                    keepMounted
                    transformOrigin={{
                        vertical: 'top',
                        horizontal: 'right',
                    }}
                    open={Boolean(anchorElUser)}
                    onClose={handleCloseUserMenu}
                >
                
                    { isAuthenticated && role === "Hr" ?
                    <MenuItem onClick={handleCloseUserMenu}>
                        <Typography textAlign="center"><Link style={{ textDecoration: "none", color: palette.secondary.main }} to="/admin/jobs">Admin Paneli</Link></Typography>
                    </MenuItem>
                    :
                    <MenuItem onClick={handleCloseUserMenu}>
                        <Typography textAlign="center"><Link style={{ textDecoration: "none", color: palette.secondary.main }} to="/user/info">Kullanıcı Paneli</Link></Typography>
                    </MenuItem>
                    }
                    {
                        !isAuthenticated ?

                            <MenuItem onClick={handleCloseUserMenu}>
                                <Typography textAlign="center"><Link style={{ textDecoration: "none", color: palette.secondary.main }} to="/login">Giriş Yap</Link></Typography>
                            </MenuItem> :

                            <MenuItem onClick={logOut}>
                                <Typography style={{ textDecoration: "none", color: palette.secondary.main }} textAlign="center">Çıkış Yap</Typography>
                            </MenuItem>
                    }


                </Menu>
            </Box>
            }
        </Toolbar>
    </Container>
    </AppBar>
  );
}
export default Navbar;
