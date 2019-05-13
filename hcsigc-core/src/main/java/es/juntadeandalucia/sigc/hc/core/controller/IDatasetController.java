package es.juntadeandalucia.sigc.hc.core.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import es.juntadeandalucia.sigc.hc.core.bo.DatasetBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.file.IData;

public interface IDatasetController extends Serializable {

	List<DatasetBO> getAllDatasets();

	/**
	 * Retrieves Datasets applying pagination
	 *
	 * @param start the index of the first dataset
	 * @param size  of the page
	 *
	 * @return a subset of Datasets
	 */
	public PaginatedBO<DatasetBO> paginate(int start, int size, String checks, String sorts, String startDate,
			String endDate);

	DatasetBO findById(Long id);

	Long createDataset(DatasetBO datasetBO);

	void updateDataset(DatasetBO datasetBO);

	void removeDataset(DatasetBO datasetBO);

	public PaginatedBO<DatasetBO> findByField(String field, String match);

	PaginatedBO<DatasetBO> search(int start, int size, String match, String searchBy, String checks, String sorts,
			String startDate, String endDate);
	
	Long uploadData(DatasetBO datasetBO, IData<? extends Iterable<String>> data) throws IOException;

	void removeBatchDataUpload(long id);

	void removeDataUpload(long id);

}
