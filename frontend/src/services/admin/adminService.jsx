import { api } from "@/services/api/apiConfig";
// src/services/adminService.js

export { getAllUsers } from "../api/authService";

export { getAllTrainers } from "../trainer/trainerService";

export { getAllMemberships } from "../member/membershipService";

export { getAllClasses } from "../shared/classService";

export { getAllFeedbacks } from "../member/feedbackService";

export const getPendingTrainers = () => api.get("/users/pending-trainers").then((res) => res.data);

export const approveTrainer = (id) => api.put(`/users/approve-trainer/${id}`);

export const updateUserRole = (userId, newRole) => api.put(`/users/update-role/${userId}?newRole=${newRole}`);
