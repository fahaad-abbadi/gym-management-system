import { api } from './apiConfig';

export const getAttendance = () => api.get('/attendance');
export const markAttendance = (data) => api.post('/attendance', data);
