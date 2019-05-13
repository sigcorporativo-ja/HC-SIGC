package es.juntadeandalucia.sigc.hc.core.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.juntadeandalucia.sigc.hc.core.bo.DBConnectionBO;
import es.juntadeandalucia.sigc.hc.core.bo.GroupBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.bo.UserBO;
import es.juntadeandalucia.sigc.hc.core.controller.IDBConnectionController;
import es.juntadeandalucia.sigc.hc.persistence.dao.IDBConnectionDAO;
import es.juntadeandalucia.sigc.hc.persistence.dao.IDBMSDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.DBConnection;
import es.juntadeandalucia.sigc.hc.persistence.entity.DBMS;

@Transactional
public class DBConnectionController implements IDBConnectionController {

	private static final Log LOG = LogFactory.getLog(DBConnectionController.class);

	private static final long serialVersionUID = 3748474403238649396L;

	@Inject
	private IDBConnectionDAO dbConnectionDao;
	
	@Inject
	private UserController userController;

	@Inject
	private IDBMSDAO dbmsDAO;
	
	@Override
	public List<DBConnectionBO> getAllDBConnections() {
		List<DBConnectionBO> result = dbConnectionDao.findAll().stream().map(DBConnectionBO::new).collect(Collectors.toList());
		LOG.info("List of DBConnections");
		return result;
	}

	@Override
	public PaginatedBO<DBConnectionBO> paginate(int start, int size) {
		String[] uoPermissions = {"ADMIN_UO"};
		UserBO sessionUser = userController.getSessionUser();
		
		List<DBConnectionBO> data = new ArrayList<>();
		Long count = null;
		if (!userController.hasPermissions(sessionUser, uoPermissions)) {
			data = dbConnectionDao.paginateByOrganizationUnit(start, size, sessionUser.getOrganizationUnit().getName()).stream().map(DBConnectionBO::new).collect(Collectors.toList());
			count = dbConnectionDao.countByOrganizationUnit(sessionUser.getOrganizationUnit().getName());
		}else {
			data = dbConnectionDao.paginate(start, size).stream().map(DBConnectionBO::new).collect(Collectors.toList());
			count = dbConnectionDao.count();
		}
		
		PaginatedBO<DBConnectionBO> result = new PaginatedBO<>(count, data);
		LOG.info("Paginate DBConnections");
		return result;
	}

	@Override
	public void createDBConnection(DBConnectionBO dbConnectionBO) {
		String[] uoPermissions = {"ADMIN_UO"};
		UserBO sessionUser = userController.getSessionUser();
		
		// If any organization is contained in the dbConnection that I want to edit, change the variable to true
		boolean sameOrganizationUnit = false;
		for (GroupBO g : dbConnectionBO.getGroups()) {
			sameOrganizationUnit = g.getOrganization().getId().equals(sessionUser.getOrganizationUnit().getId());
			if (sameOrganizationUnit)
				break;
		}
		
		if (userController.hasPermissions(sessionUser, uoPermissions)
				|| sameOrganizationUnit) {
			DBConnection dbConnection = dbConnectionBO.toEntity();
			DBMS dbms = dbmsDAO.findById(dbConnectionBO.getDbms().getId());
			dbConnection.setDbms(dbms);
			dbConnectionDao.create(dbConnection);
			LOG.info("Object DBConnectionBO created");
		}else {
			 // TODO revisar permisos para usuario en la UO ADMIN_GROUP y ADMIN_DATABASES
			LOG.info("Create object DBConnectionBO require permissions revision");
		}
		
	}

	@Override
	public void updateDBConnection(DBConnectionBO dbConnectionBO) {
		String[] uoPermissions = {"ADMIN_UO"};
		UserBO sessionUser = userController.getSessionUser();
		
		// If any organization is contained in the dbConnection that I want to edit, change the variable to true
		boolean sameOrganizationUnit = false;
		for(GroupBO g: dbConnectionBO.getGroups()) {
			sameOrganizationUnit = g.getOrganization().getId().equals(sessionUser.getOrganizationUnit().getId());
			if(sameOrganizationUnit)
				break;
		}
		
		if (userController.hasPermissions(sessionUser, uoPermissions)
				|| sameOrganizationUnit) {
			
			DBConnection dbConnection = dbConnectionDao.findById(dbConnectionBO.getId());
			dbConnection.setName(dbConnectionBO.getName());
			dbConnection.setHost(dbConnectionBO.getHost());
			dbConnection.setPort(dbConnectionBO.getPort());
			dbConnection.setDatabase(dbConnectionBO.getDatabase());
			dbConnection.setSchema(dbConnectionBO.getSchema());
			dbConnection.setUser(dbConnectionBO.getUser());
			if (!(dbConnectionBO.getPassword() == null || dbConnectionBO.getPassword().isEmpty())) {
				dbConnection.setPassword(dbConnectionBO.getPassword());
			}
			DBMS dbms = dbmsDAO.findById(dbConnectionBO.getDbms().getId());
			
			dbConnection.setDbms(dbms);
			dbConnectionDao.save(dbConnection);
			LOG.info("Object DBConnectionBO updated");
		}else {
			// TODO revisar permisos para usuario en la UO ADMIN_GROUP y ADMIN_DATABASES
			LOG.info("Update object DBConnectionBO require permissions revision");
		}
	}

	@Override
	public void removeDBConnection(DBConnectionBO dbConnectionBO) {
		String[] uoPermissions = {"ADMIN_UO"};
		UserBO sessionUser = userController.getSessionUser();
		
		// If any organization is contained in the dbConnection that I want to edit, change the variable to true
		boolean sameOrganizationUnit = false;
		for (GroupBO g : dbConnectionBO.getGroups()) {
			sameOrganizationUnit = g.getOrganization().getId().equals(sessionUser.getOrganizationUnit().getId());
			if (sameOrganizationUnit)
				break;
		}
		
		if (userController.hasPermissions(sessionUser, uoPermissions)
				|| sameOrganizationUnit) {
			DBConnection dbConnection = dbConnectionBO.toEntity();
			dbConnectionDao.remove(dbConnection);
			LOG.info("Object DBConnectionBO deleted");
		}else {
			// TODO revisar permisos para usuario en la UO ADMIN_GROUP y ADMIN_DATABASES
			LOG.info("Remove object DBConnectionBO require permissions revision");
		}
	}

	@Override
	public DBConnectionBO findById(Long id) {
		return new DBConnectionBO(dbConnectionDao.findById(id), true);
	}

	@Override
	public PaginatedBO<DBConnectionBO> findByField(String field, String match) {
		List<DBConnectionBO> data = dbConnectionDao.findByField(field, match).stream().map(DBConnectionBO::new).collect(Collectors.toList());
		Long count = (long) data.size();
		PaginatedBO<DBConnectionBO> result = new PaginatedBO<>(count, data);
		LOG.info("Paginate with field DBConnections");
		return result;
	}
	
	@Override
	public PaginatedBO<DBConnectionBO> search(int start, int size, String match, String searchBy) {
		String queryAtributes = prepareSearch(searchBy);
		
		String[] uoPermissions = {"ADMIN_UO"};
		UserBO sessionUser = userController.getSessionUser();
		
		List<DBConnectionBO> data = new ArrayList<>();
		Long count = null;
		if (!userController.hasPermissions(sessionUser, uoPermissions)) {
			data = dbConnectionDao.searchByOrganizationUnit(start, size, match.toLowerCase(), queryAtributes, sessionUser.getOrganizationUnit().getName()).stream().map(DBConnectionBO::new).collect(Collectors.toList());
			count = (long) dbConnectionDao.countSearchByOrganizationUnit(match.toLowerCase(), queryAtributes, sessionUser.getOrganizationUnit().getName());
		}else {
			data = dbConnectionDao.search(start, size, match.toLowerCase(), queryAtributes).stream().map(DBConnectionBO::new).collect(Collectors.toList());
			count = (long) dbConnectionDao.countSearch(match.toLowerCase(), queryAtributes);
		}
		
		PaginatedBO<DBConnectionBO> result = new PaginatedBO<>(count, data);
		LOG.info("Object PaginatedBO<DBConnectionBO> using search found");
		return result;
	}
	
	private String prepareSearch(String searchBy){
		String[] arraySearchBy= searchBy.split(",");
		
		//Create the query with the attributes to search
		String queryAtributes = "lower(e.dbms.name) like ?1";
		for(String atribute: arraySearchBy) {
			queryAtributes += " or lower(e." + atribute + ") like ?1";
		}
		
		return queryAtributes;
	}

}
