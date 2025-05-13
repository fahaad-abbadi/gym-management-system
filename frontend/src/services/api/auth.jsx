// src/services/api/auth.js
import { api } from './apiConfig';

const AuthAPI = {
  register: (data) => api.post('/auth/register', data).then(res => res.data),
  login: (data) => api.post('/auth/login', data).then(res => res.data),
  current: () => api.get('/users/current').then(res => res.data),
  getAll: () => api.get('/users/all').then(res => res.data),
  getById: (id) => api.get(`/users/${id}`).then(res => res.data),
  update: (id, data) => api.put(`/users/update/${id}`, data).then(res => res.data),
  delete: (id) => api.delete(`/users/delete/${id}`).then(res => res.data),
};

export default AuthAPI;
