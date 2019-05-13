import {getRequest, postRequest, deleteRequest, putRequest} from './ApiService';

const CONTEXT = '/ou';
const CONTEXT_PAGINATE = '/ou/paginate';
const CONTEXT_SEARCH = '/ou/search';
const CONTEXT_MYOU = '/ou/myou';

export const getAllOrganizationUnits = () => {
  return getRequest(CONTEXT);
};

export const getPaginateOrganizationUnits = (start, size) => {
  const request = `${CONTEXT_PAGINATE}?start=${start}&size=${size}`;
  return getRequest(request);
};

export const getOrganizationUnitById = (id) => {
  const request = `${CONTEXT}/${id}`;
  return getRequest(request);
};

export const getMyOrganizationUnit = () => {
  const request = `${CONTEXT_MYOU}`;
  return getRequest(request);
};

export const searchOrganizationUnits = (start, size, match, searchBy) => {
  const request = `${CONTEXT_SEARCH}?start=${start}&size=${size}&match=${match}&searchBy=${searchBy}`;
  return getRequest(request);
}

export const editOrganizationUnit = (id, data) => {
  return putRequest(`${CONTEXT}/${id}`, data);
};

export const insertOrganizationUnit = (data) => {
  return postRequest(CONTEXT, data);
};

export const deleteOrganizationUnit = (id) => {
  const request = `${CONTEXT}/${id}`;
  return deleteRequest(request);
};
