import axios from 'axios';
import configuration from 'configuration';
import { pathResolve } from 'utils/utils';

const API_URL = `${configuration['front.rest.api.url']}`;
const checkStatus = (response) => {
  // raises an error in case response status is not a success
  if (response.status >= 200 && response.status < 300) { // Success status lies between 200 to 300
    return response;
  } else {
    const error = new Error(response.statusText);
    error.response = response;
    throw error;
  }
};

export const getRequest = (path) => {
  const headers = {};
  headers['Accept-Language'] = 'es';

  return axios({
    method: 'get',
    url: pathResolve(API_URL, path),
    headers: headers
  }).then(checkStatus);
};


export const postRequest = (path, data, headers = {}) => {
  headers['Accept-Language'] = 'es';

  return axios({
    method: 'post',
    url: pathResolve(API_URL, path),
    data: data,
    headers: headers
  }).then(checkStatus);
};

export const putRequest = (path, data, headers = {}, config = {}) => {
  headers['Accept-Language'] = 'es';

  return axios({
    method: 'put',
    url: pathResolve(API_URL, path),
    data: data,
    headers: headers,
    ...config
  }).then(checkStatus);
};

export const deleteRequest = (path) => {
  const headers = {};
  headers['Accept-Language'] = 'es';

  return axios({
    method: 'delete',
    url: pathResolve(API_URL, path),
    headers: headers
  }).then(checkStatus);
};
