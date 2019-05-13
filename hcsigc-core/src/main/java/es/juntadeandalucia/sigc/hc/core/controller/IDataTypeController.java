package es.juntadeandalucia.sigc.hc.core.controller;

import java.io.Serializable;
import java.util.List;

import es.juntadeandalucia.sigc.hc.core.bo.DataTypeBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;

public interface IDataTypeController extends Serializable {

	List<DataTypeBO> getAllDataType();

	public PaginatedBO<DataTypeBO> paginate(int start, int size);

	DataTypeBO findById(Long id);

	void createDataType(DataTypeBO dataTypeBO);

	void updateDataType(DataTypeBO dataTypeBO);

	void removeDataType(DataTypeBO dataTypeBO);

	public PaginatedBO<DataTypeBO> findByField(String field, String match);
}
