import { api } from '../api/apiConfig';

export const createWorkoutPlan = (data) => api.post('/workout-plans', data);
export const getWorkoutPlans = () => api.get('/workout-plans');
export const getWorkoutPlanById = (id) => api.get(`/workout-plans/${id}`);
export const updateWorkoutPlan = (id, data) => api.put(`/workout-plans/${id}`, data);
export const deleteWorkoutPlan = (id) => api.delete(`/workout-plans/${id}`);

export const addWorkoutPlan = (planData) => {
  return api.post('/workout-plans', planData); // or your actual endpoint
};
