import { api } from '../api/apiConfig';

// CREATE a new class
export const createClass = (data) => api.post('/classes', data);

// GET one class by ID
export const getClassById = (id) => api.get(`/classes/${id}`);

// GET all classes
export const getAllClasses = () => api.get('/classes');

// UPDATE class by ID
export const updateClass = (id, data) => api.put(`/classes/${id}`, data);

// DELETE a class by ID
export const deleteClass = (id) => api.delete(`/classes/${id}`);

// ENROLL a member in class
export const enrollInClass = (classId, memberId) =>
  api.post('/classes/enroll', { classId, memberId });

// GET attendance for a class
export const getAttendanceByClass = (classId) =>
  api.get(`/classes/${classId}/attendance`);
