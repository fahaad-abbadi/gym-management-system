import { api } from "../api/apiConfig";

export const getMembers = () => api.get("/members");
export const getMemberById = (id) => api.get(`/members/${id}`);
export const createMember = (data) => api.post("/members", data);
export const updateMember = (id, data) => api.put(`/members/${id}`, data);
export const deleteMember = (id) => api.delete(`/members/${id}`);

export const getAttendanceByMember = (memberId) =>
  api.get(`/members/${memberId}/attendance`);

export const getDietPlansByMember = (memberId) =>
  api.get(`/members/${memberId}/diet-plans`);

export const getWorkoutPlansByMember = (memberId) =>
  api.get(`/members/${memberId}/workout-plans`);


