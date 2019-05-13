package es.juntadeandalucia.sigc.hc.core.controller.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.juntadeandalucia.sigc.hc.core.bo.DataTypeBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.controller.IDataTypeController;
import es.juntadeandalucia.sigc.hc.persistence.dao.IDataTypeDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.DataType;

@Transactional
public class DataTypeController implements IDataTypeController {

	private static final Log LOG = LogFactory.getLog(DataTypeController.class);

	private static final long serialVersionUID = -5384040341622049291L;

	@Inject
	private IDataTypeDAO dataTypeDao;
	
	@Override
	public List<DataTypeBO> getAllDataType() {
		List<DataTypeBO> result = dataTypeDao.findAll().stream().map(DataTypeBO::new).collect(Collectors.toList());
		LOG.info("List of DataTypes");
		return result;
	}

	@Override
	public PaginatedBO<DataTypeBO> paginate(int start, int size) {
		List<DataTypeBO> data = dataTypeDao.paginate(start, size).stream().map(DataTypeBO::new).collect(Collectors.toList());
		Long count = dataTypeDao.count();
		PaginatedBO<DataTypeBO> result = new PaginatedBO<>(count, data);
		LOG.info("Paginate DataTypes");
		return result;
	}

	@Override
	public DataTypeBO findById(Long id) {
		return new DataTypeBO(dataTypeDao.findById(id), true);
	}

	@Override
	public void createDataType(DataTypeBO dataTypeBO) {
		DataType dataType = dataTypeBO.toEntity();
		dataTypeDao.create(dataType);
		LOG.info("Object DataType created");
	}

	@Override
	public void updateDataType(DataTypeBO dataTypeBO) {
		DataType dataType = dataTypeDao.findById(dataTypeBO.getId());
		dataTypeDao.save(dataType);
		LOG.info("Object DataType updated");
	}

	@Override
	public void removeDataType(DataTypeBO dataTypeBO) {
		DataType dataType = dataTypeBO.toEntity();
		dataTypeDao.remove(dataType);
		LOG.info("Object DataType deleted");
	}

	@Override
	public PaginatedBO<DataTypeBO> findByField(String field, String match) {
		List<DataTypeBO> data = dataTypeDao.findByField(field, match).stream().map(DataTypeBO::new).collect(Collectors.toList());
		Long count = (long) data.size();
		PaginatedBO<DataTypeBO> result = new PaginatedBO<>(count, data);
		LOG.info("Paginate with field DataType");
		return result;
	}

}
