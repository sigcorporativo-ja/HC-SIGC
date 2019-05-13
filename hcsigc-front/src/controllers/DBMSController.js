import {
  searchDBMSs as searchDMSsService,
  getAllDBMS as getAllDBMSService,
  getPaginateDBMS as getPaginateDBMSService,
  getDBMSById as getDBMSByIdService,
  editDBMS as editDBMSService,
  insertDBMS as insertDBMSService,
  deleteDBMS as deleteDBMSService
} from 'services/DBMSService';

export const getAllDBMS = () => getAllDBMSService().then(response => response.data);
export const getPaginateDBMS = (start, end) => getPaginateDBMSService(start, end).then(response => response.data);
export const getDBMSById = (id) => getDBMSByIdService(id).then(response => response.data);
export const DeleteDBMS = (id) => deleteDBMSService(id);
export const EditDBMS = (id, data) => {
  return editDBMSService(id, data);
};
export const addDBMS = (data) => {
  return insertDBMSService(data);
};
export const searchDBMSs = (start, size, match, searchBy) => searchDMSsService(start, size, match, searchBy).then(response => response.data);
