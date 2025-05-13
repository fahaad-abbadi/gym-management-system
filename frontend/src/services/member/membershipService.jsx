import { api } from "../api/apiConfig";

export const getAllMemberships = () => api.get("/memberships");
export const createMembership = (data) => api.post("/memberships", data);
export const updateMembership = (id, data) =>
  api.put(`/memberships/${id}`, data);

// inside membershipService.jsx
export const getCurrentUser = () => {
  // however you're storing the current user, e.g., from localStorage
  return JSON.parse(localStorage.getItem("user"));
};

export const getMembershipsByMember = (memberId) =>
  api.get(`/members/${memberId}/memberships`); // Use your real API route
