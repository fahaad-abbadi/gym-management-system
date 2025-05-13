// src/services/api/apiConfig.js
import axios from 'axios';
import CryptoJS from 'crypto-js';

const BASE_URL = 'http://localhost:5000/api';
const ENCRYPTION_KEY = 'phegon-dev-inventory';

export const encrypt = (data) =>
  CryptoJS.AES.encrypt(data, ENCRYPTION_KEY).toString();

export const decrypt = (data) => {
  const bytes = CryptoJS.AES.decrypt(data, ENCRYPTION_KEY);
  return bytes.toString(CryptoJS.enc.Utf8);
};

export const saveToken = (token) =>
  localStorage.setItem('token', encrypt(token));

export const getToken = () => {
  const token = localStorage.getItem('token');
  if (!token) return null;
  try {
    return decrypt(token);
  } catch {
    return null;
  }
};

export const saveRole = (role) =>
  localStorage.setItem('role', encrypt(role));

export const getRole = () => {
  const role = localStorage.getItem('role');
  if (!role) return null;
  try {
    return decrypt(role);
  } catch {
    return null;
  }
};

export const clearAuth = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('role');
};

export const isAuthenticated = () => !!getToken();
export const isAdmin = () => getRole() === 'ADMIN';

export const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use((config) => {
  const token = getToken();
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

console.log("Auth check:", isAuthenticated());
console.log("Raw token:", localStorage.getItem("token"));
console.log("Decrypted token:", getToken());

