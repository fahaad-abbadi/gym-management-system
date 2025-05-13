import { getRole } from './apiConfig';

export const isAdmin = () => getRole() === 'ADMIN';
export const isTrainer = () => getRole() === 'TRAINER';
export const isMember = () => getRole() === 'MEMBER';
