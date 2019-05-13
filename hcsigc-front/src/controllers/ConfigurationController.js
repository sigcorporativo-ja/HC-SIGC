import {searchConfigurations as searchConfigurationsService, getAllConfiguration as getAllConfigurationService, getPaginateConfiguration as getPaginateConfigurationService, getConfigurationById as getConfigurationByIdService, editConfiguration as editConfigurationService} from 'services/ConfigurationService';

export const getAllConfiguration = () => getAllConfigurationService().then(response => response.data);
export const getPaginateConfiguration = (start, end) => getPaginateConfigurationService(start, end).then(response => response.data);
export const getConfigurationById = (id) => getConfigurationByIdService(id).then(response => response.data);
export const editConfiguration = (id, data) => {
  return editConfigurationService(id, data);
};
export const searchConfigurations = (start, size, match, searchBy) => searchConfigurationsService(start, size, match, searchBy).then(response => response.data);
