import { api } from '../api/apiConfig';

export const getAllInventory = () => api.get('/inventory');
export const createInventoryItem = (data) => api.post('/inventory', data);
export const updateInventoryItem = (id, data) => api.put(`/inventory/${id}`, data);
export const deleteInventoryItem = (id) => api.delete(`/inventory/${id}`);

export const addInventoryItem = (itemData) =>
  api.post('/inventory', itemData); // or your actual endpoint
