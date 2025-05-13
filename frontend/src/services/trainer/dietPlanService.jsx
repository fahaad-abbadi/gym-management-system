import { api } from '../api/apiConfig';

export const getAllDietPlans = () => api.get('/diet-plans');
export const createDietPlan = (data) => api.post('/diet-plans', data);
export const updateDietPlan = (id, data) => api.put(`/diet-plans/${id}`, data);
export const deleteDietPlan = (id) => api.delete(`/diet-plans/${id}`);
