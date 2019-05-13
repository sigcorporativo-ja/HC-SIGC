import {
  editOrganizationUnit as editOrganizationUnitService,
  getOrganizationUnitById as getOrganizationUnitByIdService,
  deleteOrganizationUnit as deleteOrganizationUnitService,
  insertOrganizationUnit,
  getAllOrganizationUnits as getAllOrganizationUnitsService,
  getPaginateOrganizationUnits as getPaginateOrganizationUnitsService,
  searchOrganizationUnits as searchOrganizationUnitsService,
  getMyOrganizationUnit as getMyOrganizationUnitService
} from 'services/OrganizationUnitService';

export const getAllOrganizationUnits = () => getAllOrganizationUnitsService().then(response => response.data);
export const getPaginateOrganizationUnits = (start, end) => getPaginateOrganizationUnitsService(start, end).then(response => response.data);
export const getOrganizationUnitById = (id) => getOrganizationUnitByIdService(id).then(response => response.data);
export const DeleteOrganizationUnit = (id) => deleteOrganizationUnitService(id);
export const EditOrganizationUnit = (id, data) => {
  return editOrganizationUnitService(id, data);
};
export const AddOrganizationUnit = (data) => {
  return insertOrganizationUnit(data);
};
export const searchOrganizationUnits = (start, size, match, searchBy) => searchOrganizationUnitsService(start, size, match, searchBy).then(response => response.data);
export const getMyOrganizationUnit = () => getMyOrganizationUnitService().then(response => response.data);
