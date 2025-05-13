import { api } from '../api/apiConfig';

export const getTrainerSchedules = (trainerId) => api.get(`/trainer-schedule/${trainerId}`);
export const createTrainerSchedule = (data) => api.post('/trainer-schedule', data);
export const updateTrainerSchedule = (id, data) => api.put(`/trainer-schedule/${id}`, data);
export const deleteTrainerSchedule = (id) => api.delete(`/trainer-schedule/${id}`);

