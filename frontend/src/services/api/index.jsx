// src/services/api/index.js
import AuthAPI from './auth';
import { api, saveToken, saveRole, getToken, getRole, clearAuth, isAuthenticated, isAdmin } from './apiConfig';

const ApiService = {
  // Auth
  loginUser: AuthAPI.login,
  registerUser: AuthAPI.register,
  getCurrentUser: AuthAPI.current,
  getAllUsers: AuthAPI.getAll,
  getUserById: AuthAPI.getById,
  updateUser: AuthAPI.update,
  deleteUser: AuthAPI.delete,

  // Auth Helpers
  saveToken,
  saveRole,
  getToken,
  getRole,
  clearAuth,
  isAuthenticated,
  isAdmin,

  // Add more like products, categories, etc.
};

export default ApiService;
