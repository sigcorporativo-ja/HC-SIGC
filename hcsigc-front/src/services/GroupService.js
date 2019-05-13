import {getRequest, postRequest, deleteRequest, putRequest} from './ApiService';

const CONTEXT = '/groups';
const CONTEXT_PAGINATE = '/groups/paginate';
const CONTEXT_SEARCH = '/groups/search';
const CONTEXT_GROUPS_SHARE = '/groups/toshare'

export const getAllGroups = () => {
  return getRequest(CONTEXT);
};

export const getPaginateGroups = (start, size) => {
  const request = `${CONTEXT_PAGINATE}?start=${start}&size=${size}`;
  return getRequest(request);
};
export const getGroupById = (id) => {
  const request = `${CONTEXT}/${id}`;
  return getRequest(request);
};

export const getGroupsToShare = () => {
  return getRequest(CONTEXT_GROUPS_SHARE);
}

export const searchGroups = (start, size, match, searchBy) => {
  const request = `${CONTEXT_SEARCH}?start=${start}&size=${size}&match=${match}&searchBy=${searchBy}`;
  return getRequest(request);
}

export const editGroup = (id, data) => {
  return putRequest(`${CONTEXT}/${id}`, data);
};

export const insertGroup = (data) => {
  return postRequest(CONTEXT, data);
};

export const deleteGroup = (id) => {
  const request = `${CONTEXT}/${id}`;
  return deleteRequest(request);
};
