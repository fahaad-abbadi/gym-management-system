import { api, saveRole } from './apiConfig';

export const loginUser = (data) => api.post('/auth/login', data);
export const registerUser = (data) => api.post('/auth/register', data);
export const getCurrentUser = () => api.get('/users/current');
export const getAllUsers = () => api.get('/users/all');
export const getUserById = (id) => api.get(`/users/${id}`);
export const updateUser = (id, data) => api.put(`/users/update/${id}`, data);
export const deleteUser = (id) => api.delete(`/users/delete/${id}`);

export const approveTrainer = (id) => api.put(`/trainers/approve/${id}`);
export const getPendingTrainers = () => api.get('/trainers/pending');

export default saveRole;


