import React from 'react'
import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';

const AdminRoute = ({ children }) => {

    const { isAuthenticated, role } = useSelector((state) => state.signIn);
    return isAuthenticated && role === "Hr" ? children : <Navigate to="/" />;
}

export default AdminRoute