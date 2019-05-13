import { getRequest } from './ApiService';

const CONTEXT = '/roles';

export const getAllRoles = () => {
  return getRequest(CONTEXT);
};

export const getRoleById = (id) => {
  const request = `${CONTEXT}/${id}`;
  return getRequest(request);
};
