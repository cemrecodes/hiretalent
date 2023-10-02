import * as React from 'react';
import { styled, alpha } from '@mui/material/styles';
import InputBase from '@mui/material/InputBase';
import SearchIcon from '@mui/icons-material/Search';
import { Box, Button, Container, Stack } from '@mui/material';
import LoadingBox from '../../../component/LoadingBox';
import { useState } from 'react';
import { searchApplicantAction } from '../../../redux/actions/adminAction';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux/es/hooks/useSelector';
import ApplicantCard from '../../../component/ApplicantCard';
import DeleteIcon from '@mui/icons-material/Delete';

const SearchContainer = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
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
    flex: 1, // Take available space within the flex container
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
      width: '100%'
    },
  }));
  
  const DashUsers = () => {
    const [text,setText] = useState("");
    const dispatch = useDispatch();
    const { applicants, loading } = useSelector((state) => state.searchResults);
    const { token } = useSelector(state => state.signIn);

    const handleChange = (value) => {
        setText(value);
    }

    const handleClick = () => {
        dispatch(searchApplicantAction(token, text))
        setText("");
    };
    
    const cleanText = () => {
      setText("");
    };

    return (
      <>
        <SearchContainer>
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
                        {   
                            !loading ? 
                            applicants.map(
                                (applicant) => (
                                <ApplicantCard applicant={applicant}></ApplicantCard>
                            )
                            )
                            : 
                            (
                            <LoadingBox/>
                            )}
                        </Box>
                    </Stack>
                </Container>
      </>
    )
  }
  
export default DashUsers;
