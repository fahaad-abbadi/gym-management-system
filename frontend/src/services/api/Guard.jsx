// src/services/api/Guard.jsx
import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { isAuthenticated, getRole } from '@/services/api/apiConfig';

const ProtectedRoute = ({ element, allowedRoles = [] }) => {
  const location = useLocation();

  const authed = isAuthenticated();
  const role = getRole();

  console.log("🔐 Auth:", authed);
  console.log("🎭 Role:", role);

  if (!authed) {
    return <Navigate to="/login" replace state={{ from: location }} />;
  }

  if (allowedRoles.length && !allowedRoles.includes(role)) {
    return <Navigate to="/unauthorized" replace />;
  }

  return element;
};

export default ProtectedRoute;
