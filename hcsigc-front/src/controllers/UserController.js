import {
  getUserById as getUserByIdService,
  insertUser,
  getAllUsers as getAllUsersService,
  getPaginateUsers as getPaginateUsersService,
  deleteUsers as deleteUsersService,
  editUsers as EditUsersService,
  getAllPermissions as getAllPermissionsService,
  getPermissionsGrant as getPermissionsGrantService,
  searchUsers as searchUsersService,
  getUserLogged as getUserLoggedService
} from 'services/UserService';

import {getAllRoles as getAllRolesService, getRoleById as getRoleByIdService} from 'services/RoleService';
import {modelToJson} from 'utils/utils';

export const getAllUsers = () => getAllUsersService().then(response => response.data);
export const getPaginateUsers = (start, end) => getPaginateUsersService(start, end).then(response => response.data);
export const getUserById = (id) => getUserByIdService(id).then(response => response.data);
export const DeleteUsers = (id) => deleteUsersService(id);
export const ViewUsers = (id) => alert(`View ${id}`);
export const EditUsers = (id, data) => {
  delete data.password_confirm;
  const dataModel = parseDataUsers(data);
  return EditUsersService(id, dataModel);
};
export const addUser = (data) => {
  delete data.password_confirm;
  const dataModel = parseDataUsers(data);
  return insertUser(dataModel);
};
export const getAllPermissions = () => getAllPermissionsService().then(response => response.data);
export const getPermissionsGrant = (id) => getPermissionsGrantService(id).then(response => response.data)
export const searchUsers = (start, size, match, searchBy) => searchUsersService(start, size, match, searchBy).then(response => response.data);
export const getUserLogged = () => getUserLoggedService().then(response => modelToJson(response.data));

export const parseDataUsers = (data) => {

  if (data.org_unit.id === undefined) {
    const validOrgUnit = {
      id: data.org_unit.value,
      name: data.org_unit.label
    };

    data.org_unit = validOrgUnit;
  }
  return data;
}

export const getAllRoles = () => getAllRolesService().then(response => response.data);
export const getRolesGrant = (permissions) => getAllRolesService().then(response => {
  const roles = response.data;
  return roles.filter(role => role.permissions.every(rolePermission => permissions.find(permission => permission.id === rolePermission.id) !== undefined));
});

export const getRoleById = id => getRoleByIdService(id).then(response => response.data);
