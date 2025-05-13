import { api } from './apiConfig';

export const getWorkoutSessions = () => api.get('/workout-sessions');
export const createWorkoutSession = (data) => api.post('/workout-sessions', data);
export const updateWorkoutSession = (id, data) => api.put(`/workout-sessions/${id}`, data);
export const deleteWorkoutSession = (id) => api.delete(`/workout-sessions/${id}`);
