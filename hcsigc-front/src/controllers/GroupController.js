import {
  editGroup as editGroupService,
  getGroupById as getGroupByIdService,
  deleteGroup as deleteGroupService,
  insertGroup,
  getAllGroups as getAllGroupsService,
  getPaginateGroups as getPaginateGroupsService,
  searchGroups as searchGroupsService,
  getGroupsToShare as getGroupsToShareService
} from 'services/GroupService';

export const getAllGroups = () => getAllGroupsService().then(response => response.data);
export const getPaginateGroups = (start, end) => getPaginateGroupsService(start, end).then(response => response.data);
export const getGroupById = (id) => getGroupByIdService(id).then(response => response.data);
export const getGroupsToShare = () => getGroupsToShareService().then(response => response.data);
export const DeleteGroup = (id) => deleteGroupService(id);
export const EditGroup = (id, data) => {
  const validData = parseDataGroup(data);
  return editGroupService(id, validData);
};
export const addGroup = (data) => {
  const validData = parseDataGroup(data);
  return insertGroup(validData);
};

export const parseDataGroup = (data) => {
  let validDBConnections = [];
  let validUsers = [];
  const validOrgUnit = {
    id: data.org_unit.value,
    name: data.org_unit.label
  };

  if (data.dbconnections[0] !== undefined) {
    validDBConnections = data.dbconnections.map(db => {
      const aObj = {};
      if (db.id == null) {
        aObj['id'] = db.value;
        aObj['name'] = db.label;
      }
      return aObj;
    });
  }

  if (data.users[0] !== undefined) {
    validUsers = data.users.map(user => {
      const aObj = {};
      if (user.id == null) {
        aObj['id'] = user.value;
        aObj['name'] = user.label;
      }
      return aObj;
    });
  }

  if (data.org_unit.id === undefined) 
    data.org_unit = validOrgUnit;
  
  if (data.dbconnections[0] !== undefined) 
    data.dbconnections = (validDBConnections.length > 0 && validDBConnections[0].id !== undefined)
      ? validDBConnections
      : data.dbconnections

  if (data.users[0] !== undefined) 
    data.users = (validUsers.length > 0 && validUsers[0].id !== undefined)
      ? validUsers
      : data.users

  return data;
}
export const searchGroups = (start, size, match, searchBy) => searchGroupsService(start, size, match, searchBy).then(response => response.data);
