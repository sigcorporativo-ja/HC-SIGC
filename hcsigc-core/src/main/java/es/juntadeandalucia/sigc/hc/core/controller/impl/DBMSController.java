package es.juntadeandalucia.sigc.hc.core.controller.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.juntadeandalucia.sigc.hc.core.bo.DBMSBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.controller.IDBMSController;
import es.juntadeandalucia.sigc.hc.persistence.dao.IDBMSDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.DBMS;

@Transactional
public class DBMSController implements IDBMSController {

	private static final Log LOG = LogFactory.getLog(DBMSController.class);
	
	private static final long serialVersionUID = -1741939295501645942L;

	@Inject
	private IDBMSDAO dbmsDao;

	@Override
	public List<DBMSBO> getAllDBMS() {
		List<DBMSBO> result = dbmsDao.findAll().stream().map(DBMSBO::new).collect(Collectors.toList());
		LOG.info("List of DBMSs");
		return result;
	}


	@Override
	public PaginatedBO<DBMSBO> paginate(int start, int size) {
		List<DBMSBO> data = dbmsDao.paginate(start, size).stream().map(DBMSBO::new).collect(Collectors.toList());
		Long count = dbmsDao.count();
		PaginatedBO<DBMSBO> result = new PaginatedBO<>(count, data);
		LOG.info("Paginate DBMSs");
		return result;
	}

	@Override
	public void updateDBMS(DBMSBO dmbsBO) {
		DBMS dbms = dbmsDao.findById(dmbsBO.getId());
		dbms.setName(dmbsBO.getName());
		dbms.setConnectionRegex(dmbsBO.getConnectionRegex());
		dbmsDao.save(dbms);
		LOG.info("Object DBMS updated");
	}

	@Override
	public void createDBMS(DBMSBO dmbsBO) {
		DBMS dbms = dmbsBO.toEntity();
		dbmsDao.create(dbms);
		LOG.info("Object DBMS created");
	}

	@Override
	public void removeDBMS(DBMSBO dmbsBO) {
		DBMS dbms = dmbsBO.toEntity();
		dbmsDao.remove(dbms);
		LOG.info("Object DBMS deleted");
	}

	@Override
	public DBMSBO findById(Long id) {
		return new DBMSBO(dbmsDao.findById(id), true);
	}

	@Override
	public PaginatedBO<DBMSBO> findByField(String field, String match) {
		List<DBMSBO> data = dbmsDao.findByField(field, match).stream().map(DBMSBO::new).collect(Collectors.toList());
		Long count = (long) data.size();
		PaginatedBO<DBMSBO> result = new PaginatedBO<>(count, data);
		LOG.info("Paginate with field DBMS");
		return result;
	}
	
	@Override
	public PaginatedBO<DBMSBO> search(int start, int size, String match, String searchBy) {
		String queryAtributes = prepareSearch(searchBy);
		
		List<DBMSBO> data = dbmsDao.search(start, size, match.toLowerCase(), queryAtributes).stream().map(DBMSBO::new).collect(Collectors.toList());
		Long count = (long) dbmsDao.countSearch(match.toLowerCase(), queryAtributes);
		PaginatedBO<DBMSBO> result = new PaginatedBO<>(count, data);
		LOG.info("Object PaginatedBO<DBMSBO> using search found");
		return result;
	}
	
	private String prepareSearch(String searchBy){
		String[] arraySearchBy= searchBy.split(",");
		
		//Create the query with the attributes to search
		String queryAtributes = "";
		for(String atribute: arraySearchBy) {
			queryAtributes += "lower(e." + atribute + ") like ?1";
			if(arraySearchBy[arraySearchBy.length - 1] != atribute)
				queryAtributes += " or ";
		}
		
		return queryAtributes;
	}
}
