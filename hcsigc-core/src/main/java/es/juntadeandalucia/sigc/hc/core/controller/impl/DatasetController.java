package es.juntadeandalucia.sigc.hc.core.controller.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.juntadeandalucia.sigc.hc.core.bo.DatasetBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.bo.UserBO;
import es.juntadeandalucia.sigc.hc.core.controller.IDatasetController;
import es.juntadeandalucia.sigc.hc.core.file.IConnector;
import es.juntadeandalucia.sigc.hc.core.file.IData;
import es.juntadeandalucia.sigc.hc.core.file.impl.LocalConnector;
import es.juntadeandalucia.sigc.hc.persistence.dao.IDatasetDAO;
import es.juntadeandalucia.sigc.hc.persistence.dao.IGroupDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.Dataset;
import es.juntadeandalucia.sigc.hc.persistence.entity.User;

@Transactional
public class DatasetController implements IDatasetController {

	private static final Log LOG = LogFactory.getLog(DatasetController.class);

	private static final long serialVersionUID = -8961929758927141882L;

	@Inject
	private IDatasetDAO datasetDao;

	@Inject
	private UserController userController;

	@Inject
	private DataTypeController dataTypeController;

	@Inject
	private IGroupDAO groupDao;

	@Override
	public List<DatasetBO> getAllDatasets() {
		List<DatasetBO> result = datasetDao.findAll().stream().map(DatasetBO::new).collect(Collectors.toList());
		LOG.info("List of Datasets");
		return result;
	}

	@Override
	public PaginatedBO<DatasetBO> paginate(int start, int size, String checks, String querySorts, String startDate,
			String endDate) {
		String[] superAdmin = { "ADMIN_UO" };
		String[] uoPermissions = { "SHARE_DATA" };
		UserBO sessionUser = userController.getSessionUser();
		String queryChecks = "";
		Timestamp queryStartDate = null;
		Timestamp queryEndDate = null;
		
		PaginatedBO<DatasetBO> result = new PaginatedBO<>(0, new ArrayList<DatasetBO>());
		
		if(userController.hasPermissions(sessionUser, superAdmin) || !checks.equals("")) {
			if (!startDate.equalsIgnoreCase("") && !endDate.equalsIgnoreCase("")) {
				queryStartDate = prepareDate(startDate);
				queryEndDate = prepareDate(endDate);
			}

			if (checks != null && !checks.equals("") && sessionUser != null)
				queryChecks = prepareChecks(sessionUser.getId(), checks);

			if (!userController.hasPermissions(sessionUser, uoPermissions)) {
				// Show only the datasets with l_global = true
				queryChecks = "e.global=true";
			}

			List<DatasetBO> data = datasetDao.paginate(start, size, queryChecks, querySorts, queryStartDate, queryEndDate)
					.stream().map(DatasetBO::new).collect(Collectors.toList());
			Long count = datasetDao.count(queryChecks, queryStartDate, queryEndDate);
			 result = new PaginatedBO<>(count, data);
			LOG.info("Paginate Datasets");
		}

		return result;
	}

	@Override
	public void updateDataset(DatasetBO datasetBO) {
		String[] uoPermissions = { "ADMIN_UO" };
		UserBO sessionUser = userController.getSessionUser();
		Dataset dataset = datasetDao.findById(datasetBO.getId());
		boolean globalEstatus = dataset.isGlobal();
		User user = dataset.getUser();

		// Only edit if I am a S.A. or (global = false and I am dataset owner)
		if (userController.hasPermissions(sessionUser, uoPermissions)
				|| (globalEstatus == false && user.getId().toString().equals(sessionUser.getId()))) {
			datasetBO.setDataType(dataTypeController.findById(1L)); // TODO cambiar a partir del tipo de archivo que se
																	// ha subido
			dataset.setName(datasetBO.getName());
			dataset.setDescription(datasetBO.getDescription());
			dataset.setTableName(datasetBO.getTableName());
			dataset.setGlobal(datasetBO.isGlobal());

			// To persist the groupUsers necessary
			datasetBO.getGroups().stream().forEach(group -> {
				groupDao.findGroupUsersByGroupId(group.getId()).stream().forEach(groupUser -> {
					List<Dataset> temporal = groupUser.getDatasets();
					temporal.add(dataset);
					groupUser.setDatasets(temporal);
					LOG.info("Relation data_x_grus created");
				});
			});

			// Remove the groupUsers necessary
			dataset.getGroupsUsers().stream().forEach(groupUser -> { // All dataset groupUsers
				if (datasetBO.getGroups().size() > 0) {
					datasetBO.getGroups().stream().forEach(group -> { // All Groups selected in UI
						if (group.getId() != groupUser.getGroup().getId()) { // If the old group do not exist in the
																				// Groups selected in UI, remove the
																				// dataset
							List<Dataset> temporal = groupUser.getDatasets();
							temporal.remove(dataset);
							groupUser.setDatasets(temporal);
							LOG.info("Relation data_x_grus removed");
						}
					});
				} else { // In this case, the list of groups is empty, we will remove all relations
					List<Dataset> temporal = groupUser.getDatasets();
					temporal.remove(dataset);
					groupUser.setDatasets(temporal);
					LOG.info("Relation data_x_grus removed");
				}
			});

			datasetDao.save(dataset);
			LOG.info("Object Dataset updated");
		} else {
			LOG.info("Update object Dataset not allowed");
		}
	}

	@Override
	public Long createDataset(DatasetBO datasetBO) {
		datasetBO.setDataType(dataTypeController.findById(1L)); // TODO cambiar a partir del tipo de archivo que se ha
																// subido
		datasetBO.setUser(userController.getSessionUser());
		Dataset dataset = datasetBO.toEntity();
		Long datasetId = datasetDao.create(dataset);
		LOG.info("Object Dataset created");
		
		return datasetId;
	}

	@Override
	public Long uploadData(DatasetBO datasetBO, IData<? extends Iterable<String>> data) throws IOException {
		IConnector<? extends Iterable<String>> connector = new LocalConnector<>(data);
		
		String tempTableCreated = datasetDao.createTempTable(datasetBO.getName(), connector.getAttributeNames());
		LOG.info("TempTable created");
		String[] arrayTempTableCreated = tempTableCreated.split(",");
		datasetBO.setTableName(arrayTempTableCreated[0]);
		loadDataIntoTable(arrayTempTableCreated[0], connector);
		
		return Long.valueOf(arrayTempTableCreated[1]);
	}

	private void loadDataIntoTable(String tableName, IConnector<? extends Iterable<String>> connector)
			throws IOException {
		Collection<? extends Iterable<String>> batch;
		do {
			batch = connector.getBatch();
			datasetDao.insertBatch(tableName, batch);
		} while (!batch.isEmpty());
		LOG.info("Loaded data into table");
	}

	@Override
	public void removeDataset(DatasetBO datasetBO) {
		String[] uoPermissions = { "ADMIN_UO" };
		UserBO sessionUser = userController.getSessionUser();
		User user = datasetDao.findById(datasetBO.getId()).getUser();
		boolean globalEstatus = datasetDao.findById(datasetBO.getId()).isGlobal();

		// Only edit if I am a S.A. or (global = false and I am dataset owner)
		if (userController.hasPermissions(sessionUser, uoPermissions)
				|| (globalEstatus == false && user.getId().toString().equals(sessionUser.getId()))) {
			datasetBO.setDataType(dataTypeController.findById(1L)); // TODO cambiar a partir del tipo de archivo que se
																	// ha subido
			datasetBO.setUser(new UserBO(user));
			Dataset dataset = datasetBO.toEntity();
			datasetDao.remove(dataset);
			LOG.info("Object Dataset deleted");
		} else {
			LOG.info("Delete object Dataset not allowed");
		}
	}
	
	@Override
	public void removeBatchDataUpload(long id) {
		datasetDao.removeBatchDataUpload(id);
		LOG.info("TempData with id=" + id + " removed");
	}

	@Override
	public void removeDataUpload(long id) {
		datasetDao.removeDataUpload(id);
		LOG.info("Table associated with tempData with id=" + id + " droped");
	}
	
	@Override
	public DatasetBO findById(Long id) {
		return new DatasetBO(datasetDao.findById(id), true);
	}

	@Override
	public PaginatedBO<DatasetBO> findByField(String field, String match) {
		List<DatasetBO> data = datasetDao.findByField(field, match).stream().map(DatasetBO::new)
				.collect(Collectors.toList());
		Long count = (long) data.size();
		PaginatedBO<DatasetBO> result = new PaginatedBO<>(count, data);
		LOG.info("Paginate with field Datasets");
		return result;
	}

	@Override
	public PaginatedBO<DatasetBO> search(int start, int size, String match, String searchBy, String checks,
			String sorts, String startDate, String endDate) {
		String[] superAdmin = { "ADMIN_UO" };
		String[] uoPermissions = { "SHARE_DATA" };
		UserBO sessionUser = userController.getSessionUser();
		String queryAtributes = prepareSearch(searchBy);
		String queryChecks = "";
		Timestamp queryStartDate = null;
		Timestamp queryEndDate = null;
		
		PaginatedBO<DatasetBO> result = new PaginatedBO<>(0, new ArrayList<DatasetBO>());
		
		if(userController.hasPermissions(sessionUser, superAdmin) || !checks.equals("")) {
			if (!startDate.equalsIgnoreCase("") && !endDate.equalsIgnoreCase("")) {
				queryStartDate = prepareDate(startDate);
				queryEndDate = prepareDate(endDate);
			}

			if (checks != null && !checks.equalsIgnoreCase("") && sessionUser != null)
				queryChecks = prepareChecks(sessionUser.getId(), checks);

			if (!userController.hasPermissions(sessionUser, uoPermissions)) {
				// Show only the datasets with l_global = true
				queryChecks = "e.global=true";
			}

			List<DatasetBO> data = datasetDao.search(start, size, match.toLowerCase(), queryAtributes, queryChecks, sorts,
					queryStartDate, queryEndDate).stream().map(DatasetBO::new).collect(Collectors.toList());
			Long count = (long) datasetDao.countSearch(match.toLowerCase(), queryAtributes, queryChecks, queryStartDate,
					queryEndDate);
			result = new PaginatedBO<>(count, data);
			LOG.info("Object PaginatedBO<DatasetBO> using search found");
		}
		
		return result;
	}

	private String prepareSearch(String searchBy) {
		String[] arraySearchBy = searchBy.split(",");

		// Create the query with the attributes to search
		String queryAtributes = "lower(e.user.name) like ?1 or lower(e.user.surname) like ?1 or "
				+ "lower(e.user.username) like ?1";
		for (String atribute : arraySearchBy) {
			queryAtributes += " or lower(e." + atribute + ") like ?1";
		}

		return queryAtributes;
	}

	private Timestamp prepareDate(String stringDate) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date parsedDate = dateFormat.parse(stringDate);
			java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			return timestamp;
		} catch (ParseException e) {
			LOG.info("Exception :" + e);
			return null;
		}
	}

	private String prepareChecks(String userId, String checks) {
		String[] arrayChecks = checks.split(",");

		// Create the query with the checks selected
		String checksSelected = "";
		for (String check : arrayChecks) {
			if (check.equals("private"))
				checksSelected += "e.user.id=" + userId;
			if (check.equals("groups")) 
				checksSelected += "e.user.id!=" + userId + " and gu.user.id=" + userId;
			if (check.equals("l_global"))
				checksSelected += "e.global = TRUE";

			if (arrayChecks[arrayChecks.length - 1] != check)
				checksSelected += " or ";
		}
		
		return checksSelected;
	}

}
