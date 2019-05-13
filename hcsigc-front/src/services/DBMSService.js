import {postRequest, getRequest, putRequest, deleteRequest} from './ApiService';

const CONTEXT = '/dbms';
const CONTEXT_PAGINATE = '/dbms/paginate';
const CONTEXT_SEARCH = '/dbms/search';

export const getAllDBMS = () => {
  return getRequest(CONTEXT);
};

export const getPaginateDBMS = (start, size) => {
  const request = `${CONTEXT_PAGINATE}?start=${start}&size=${size}`;
  return getRequest(request);
};

export const getDBMSById = (id) => {
  const request = `${CONTEXT}/${id}`;
  return getRequest(request);
};

export const searchDBMSs = (start, size, match, searchBy) => {
  const request = `${CONTEXT_SEARCH}?start=${start}&size=${size}&match=${match}&searchBy=${searchBy}`;
  return getRequest(request);
}

export const editDBMS = (id, data) => {
  return putRequest(`${CONTEXT}/${id}`, data);
};

export const insertDBMS = (data) => {
  return postRequest(CONTEXT, data);
};

export const deleteDBMS = (id) => {
  const request = `${CONTEXT}/${id}`;
  return deleteRequest(request);
};
