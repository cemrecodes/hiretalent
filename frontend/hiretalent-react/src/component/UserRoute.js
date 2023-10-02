import React from 'react'
import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';

const UserRoute = ({ children }) => {

    const { role, isAuthenticated } = useSelector((state) => state.signIn);
    return isAuthenticated && role === "Applicant" ? children : <Navigate to="/" />;
}

export default UserRoute;