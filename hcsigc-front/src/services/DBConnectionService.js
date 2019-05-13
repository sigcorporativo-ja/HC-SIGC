import {postRequest, getRequest, putRequest, deleteRequest} from './ApiService';

const CONTEXT = '/dbconnection';
const CONTEXT_PAGINATE = '/dbconnection/paginate';
const CONTEXT_SEARCH = '/dbconnection/search';

export const getAllDBConnection = () => {
  return getRequest(CONTEXT);
};

export const getPaginateDBConnection = (start, size) => {
  const request = `${CONTEXT_PAGINATE}?start=${start}&size=${size}`;
  return getRequest(request);
};

export const getDBConnectionById = (id) => {
  const request = `${CONTEXT}/${id}`;
  return getRequest(request);
};

export const editDBConnection = (id, data) => {
  return putRequest(`${CONTEXT}/${id}`, data);
};

export const searchDBConnections = (start, size, match, searchBy) => {
  const request = `${CONTEXT_SEARCH}?start=${start}&size=${size}&match=${match}&searchBy=${searchBy}`;
  return getRequest(request);
}

export const insertDBConnection = (data) => {
  return postRequest(CONTEXT, data);
};

export const deleteDBConnection = (id) => {
  const request = `${CONTEXT}/${id}`;
  return deleteRequest(request);
};
