import { api } from '../api/apiConfig';

export const getEnrollments = () => api.get('/enrollments');
export const enrollMember = (data) => api.post('/enrollments', data);
export const getEnrollmentById = (id) => api.get(`/enrollments/${id}`);
export const deleteEnrollment = (id) => api.delete(`/enrollments/${id}`);
