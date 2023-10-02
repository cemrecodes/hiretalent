import React from "react";
import { Box, Typography, styled } from "@mui/material";

const Header = () => {
    const StyleHeader = styled(Box)(({theme}) => (
       { 
            display: "flex",
            justifyContent: "center",
            minHeight: 50,
            backgroundColor: theme.palette.secondary.light
            
        })

    );

    return(
        <>
            <StyleHeader>
                <Typography variant="h5" component="div" color="white"  >
                Aktif İş İlanları
                </Typography>

            </StyleHeader>
        </>
    )
}

export default Header;