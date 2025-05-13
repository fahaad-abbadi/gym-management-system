import { api } from "../api/apiConfig";

export const getAllTrainers = () => api.get("/trainers");
export const getTrainerById = (trainerId) => api.get(`/trainers/${trainerId}`);
export const createTrainer = (data) => api.post("/trainers", data);
export const updateTrainer = (trainerId, data) => api.put(`/trainers/${trainerId}`, data);
export const deleteTrainer = (trainerId) => api.delete(`/trainers/${trainerId}`);

// 1. Get Workout Plans by Trainer
export const getWorkoutPlansByTrainer = (trainerId) => api.get(`/trainer/${trainerId}/workout-plans`);

// 2. Get Upcoming Sessions by Trainer
export const getUpcomingSessionsByTrainer = (trainerId) => api.get(`/trainer/${trainerId}/upcoming-sessions`);

// 3. Get Average Rating of Trainer
export const getTrainerAverageRating = (trainerId) => api.get(`/trainer/${trainerId}/average-rating`);

