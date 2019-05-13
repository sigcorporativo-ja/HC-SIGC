import {
  searchDatasets as searchDatasetsService,
  insertDataset,
  getAllDatasets as getAllDatasetsService,
  getPaginateDatasets as getPaginateDatasetsService,
  getDatasetById as getDatasetByIdService,
  deleteDataset as deleteDatasetService,
  deleteBatchDataUpload as deleteBatchDataUploadService,
  deleteDataUpload as deleteDataUploadService,
  editDataset as editDatasetService
} from 'services/DatasetService';

export const getAllDatasets = () => getAllDatasetsService().then(response => response.data);
export const getPaginateDatasets = (start, end, checks, sorts, startDate, endDate) => getPaginateDatasetsService(start, end, checks, sorts, startDate, endDate).then(response => response.data);
export const getDatasetById = (id) => getDatasetByIdService(id).then(response => response.data);
export const DeleteDataset = (id) => deleteDatasetService(id);
export const DeleteBatchDataUpload = (dataUploadId) => deleteBatchDataUploadService(dataUploadId);
export const DeleteDataUpload = (dataUploadId) => deleteDataUploadService(dataUploadId);
export const EditDataset = (id, data) => {
  const validData = parseDataGroup(data);
  return editDatasetService(id, validData);
};
export const InfoDataset = (id) => alert(`Mostrará datos dependiendo del tipo del Dataset.`);
export const LinkDataset = (id) => alert('LinkDataset');
export const AddDataset = (data, inputFileId, onUploadProgress = () => undefined) => {
  const formData = new FormData();
  Object.keys(data).forEach(k => formData.append(k, data[k]));
  formData.append('file', document.getElementById(inputFileId).files[0]);
  formData.append('fileName', document.getElementById(inputFileId).files[0].name);
  return insertDataset(formData, onUploadProgress);
};

export const parseDataGroup = (data) => {
  let validGroups = [];

  if (data.groups[0] !== undefined) {
    validGroups = data.groups.map(db => {
      const aObj = {};
      if (db.id == null) {
        aObj['id'] = db.value;
        aObj['name'] = db.label;
      }
      return aObj;
    });
  }

  if (data.groups[0] !== undefined) 
    data.groups = (validGroups.length > 0 && validGroups[0].id !== undefined)
      ? validGroups
      : data.groups

  return data;
}

export const searchDatasets = (start, size, match, searchBy, checks, sorts, startDate, endDate) => searchDatasetsService(start, size, match, searchBy, checks, sorts, startDate, endDate).then(response => response.data);
