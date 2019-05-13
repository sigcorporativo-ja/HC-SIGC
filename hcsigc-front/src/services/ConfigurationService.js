import {getRequest, putRequest} from './ApiService';

const CONTEXT = '/configuration';
const CONTEXT_PAGINATE = '/configuration/paginate';
const CONTEXT_SEARCH = '/configuration/search';

export const getAllConfiguration = () => {
  return getRequest(CONTEXT);
};

export const searchConfigurations = (start, size, match, searchBy) => {
  const request = `${CONTEXT_SEARCH}?start=${start}&size=${size}&match=${match}&searchBy=${searchBy}`;
  return getRequest(request);
}

export const getPaginateConfiguration = (start, size) => {
  const request = `${CONTEXT_PAGINATE}?start=${start}&size=${size}`;
  return getRequest(request);
};

export const getConfigurationById = (id) => {
  const request = `${CONTEXT}/${id}`;
  return getRequest(request);
};

export const editConfiguration = (id, data) => {
  return putRequest(`${CONTEXT}/${id}`, data);
};
