import {getRequest, deleteRequest, putRequest} from './ApiService';

const CONTEXT = '/datasets';
const CONTEXT_PAGINATE = `${CONTEXT}/paginate`;
const CONTEXT_SEARCH = `${CONTEXT}/search`;
const CONTEXT_UPLOAD = `${CONTEXT}/upload`;
const CONTEXT_BATCH_UPLOAD = `${CONTEXT}/batchDataUpload`;

export const getAllDatasets = () => {
  return getRequest(CONTEXT);
};

export const getPaginateDatasets = (start, size, checks, sorts, startDate, endDate) => {
  const request = `${CONTEXT_PAGINATE}?start=${start}&size=${size}&checks=${checks}&sorts=${sorts}&startDate=${startDate}&endDate=${endDate}`;
  return getRequest(request);
};

export const getDatasetById = (id) => {
  const request = `${CONTEXT}/${id}`;
  return getRequest(request);
};

export const searchDatasets = (start, size, match, searchBy, checks, sorts, startDate, endDate) => {
  const request = `${CONTEXT_SEARCH}?start=${start}&size=${size}&match=${match}&searchBy=${searchBy}&checks=${checks}&sorts=${sorts}&startDate=${startDate}&endDate=${endDate}`;
  return getRequest(request);
}

export const deleteDataset = (id) => {
  const request = `${CONTEXT}/${id}`;
  return deleteRequest(request);
}

export const deleteBatchDataUpload = (dataUploadId) => {
  const request = `${CONTEXT_BATCH_UPLOAD}/${dataUploadId}`;
  return deleteRequest(request);
}

export const deleteDataUpload = (dataUploadId) => {
  const request = `${CONTEXT_UPLOAD}/${dataUploadId}`;
  return deleteRequest(request);
}

export const editDataset = (id, data) => {
  return putRequest(`${CONTEXT}/${id}`, data);
};

export const insertDataset = (data, onUploadProgress) => {
  const request = `${CONTEXT_UPLOAD}`;
  return putRequest(request, data, {
    'Content-Type': 'multipart/form-data'
  }, {onUploadProgress});
};
