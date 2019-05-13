import {postRequest, getRequest, deleteRequest, putRequest} from './ApiService';

const CONTEXT = '/users';
const CONTEXT_PERMISSIONS = '/permissions';
const CONTEXT_USER_PERMISSIONS = '/users/permissions_grant';
const CONTEXT_OU = '/ou';
const CONTEXT_PAGINATE = '/users/paginate';
const CONTEXT_SEARCH = '/users/search';
const CONTEXT_LOGGED = '/users/logged';

export const getAllUsers = () => {
  return getRequest(CONTEXT);
};

export const getAllOrganizationUnit = () => {
  return getRequest(CONTEXT_OU);
};

export const getPaginateUsers = (start, size) => {
  const request = `${CONTEXT_PAGINATE}?start=${start}&size=${size}`;
  return getRequest(request);
};

export const getUserById = (id) => {
  const request = `${CONTEXT}/${id}`;
  return getRequest(request);
};

export const getUserLogged = () => {
  const request = `${CONTEXT_LOGGED}`;
  return getRequest(request);
};

export const getAllPermissions = () => {
  return getRequest(CONTEXT_PERMISSIONS);
};

export const getPermissionsGrant = (id) => {
  return getRequest(`${CONTEXT_USER_PERMISSIONS}/${id}`);
};

export const searchUsers = (start, size, match, searchBy) => {
  const request = `${CONTEXT_SEARCH}?start=${start}&size=${size}&match=${match}&searchBy=${searchBy}`;
  return getRequest(request);
};

export const deleteUsers = (id) => {
  const request = `${CONTEXT}/${id}`;
  return deleteRequest(request);
}

export const editUsers = (id, data) => {
  return putRequest(`${CONTEXT}/${id}`, data);
};

export const insertUser = (data) => {
  return postRequest(CONTEXT, data);
};
