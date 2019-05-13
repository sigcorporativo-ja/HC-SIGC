package es.juntadeandalucia.sigc.hc.persistence.dao;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import es.juntadeandalucia.sigc.hc.persistence.entity.Dataset;

public interface IDatasetDAO extends IGenericDAO<Dataset, Long> {

	List<Dataset> paginate(int start, int size, String queryChecks, String querySorts, Timestamp queryStartDate,
			Timestamp queryEndDate);

	Long count(String queryChecks, Timestamp queryStartDate, Timestamp queryEndDate);

	List<Dataset> search(int start, int size, String match, String queryAtributes, String queryChecks,
			String querySorts, Timestamp queryStartDate, Timestamp queryEndDate);

	Long countSearch(String match, String queryAtributes, String queryChecks, Timestamp queryStartDate,
			Timestamp queryEndDate);

	String createTempTable(String name, Iterable<String> attributeNames);

	void insertBatch(String tableName, Collection<? extends Iterable<String>> batch);

	void removeBatchDataUpload(long id);

	void removeDataUpload(long id);

}
