import { api } from "@/services/api/apiConfig";

// Existing exports
export { getAllUsers } from "../api/authService";
export { getAllTrainers } from "../trainer/trainerService";
export { getAllMemberships } from "../member/membershipService";
export { getAllClasses } from "../shared/classService";
export { getAllFeedbacks } from "../member/feedbackService";

// Existing trainer APIs
export const getPendingTrainers = () => api.get("/users/pending-trainers").then((res) => res.data);
export const approveTrainer = (id) => api.put(`/users/approve-trainer/${id}`);

// Missing staff APIs (ADD THESE)
export const getPendingStaff = () => api.get("/users/pending-staff").then((res) => res.data);
export const approveStaff = (id) => api.put(`/users/approve-staff/${id}`);

// Utility
export const updateUserRole = (userId, newRole) => api.put(`/users/update-role/${userId}?newRole=${newRole}`);
