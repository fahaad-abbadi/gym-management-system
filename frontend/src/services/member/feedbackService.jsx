import { api } from '../api/apiConfig';

export const getAllFeedbacks = () => api.get('/feedback');
export const getFeedbackById = (id) => api.get(`/feedback/${id}`);
export const postFeedback = (data) => api.post('/feedback', data);
export const deleteFeedback = (id) => api.delete(`/feedback/${id}`);
    