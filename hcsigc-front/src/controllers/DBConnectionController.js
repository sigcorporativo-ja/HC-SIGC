import {
  searchDBConnections as searchDBConnectionsService,
  getDBConnectionById as getDBConnectionByIdService,
  insertDBConnection as insertDBConnectionService,
  getAllDBConnection as getAllDBConnectionService,
  getPaginateDBConnection as getPaginateDBConnectionService,
  deleteDBConnection as deleteDBConnectionService,
  editDBConnection as editDBConnectionService
} from 'services/DBConnectionService';

export const getAllDBConnection = () => getAllDBConnectionService().then(response => response.data);
export const getPaginateDBConnection = (start, end) => getPaginateDBConnectionService(start, end).then(response => response.data);
export const getDBConnectionById = (id) => getDBConnectionByIdService(id).then(response => response.data);
export const DeleteDBConnection = (id) => deleteDBConnectionService(id);
export const EditDBConnection = (id, data) => {
  delete data.password_confirm;
  const validData = parseDataDBConnection(data);
  return editDBConnectionService(id, validData);
};
export const addDBConnection = (data) => {
  delete data.password_confirm;
  const validData = parseDataDBConnection(data);
  return insertDBConnectionService(validData);
};

export const parseDataDBConnection = (data) => {

  if (data.dbms.id === undefined) {
    const validDBConnection = {
      id: data.dbms.value,
      name: data.dbms.label
    };

    data.dbms = validDBConnection;
  }

  return data;
}

export const searchDBConnections = (start, size, match, searchBy) => searchDBConnectionsService(start, size, match, searchBy).then(response => response.data);
