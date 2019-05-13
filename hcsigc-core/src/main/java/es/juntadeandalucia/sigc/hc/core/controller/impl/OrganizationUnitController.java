package es.juntadeandalucia.sigc.hc.core.controller.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.juntadeandalucia.sigc.hc.core.bo.OrganizationUnitBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.controller.IOrganizationUnitController;
import es.juntadeandalucia.sigc.hc.persistence.dao.IOrganizationUnitDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.OrganizationUnit;

@Transactional
public class OrganizationUnitController implements IOrganizationUnitController {

	private static final Log LOG = LogFactory.getLog(OrganizationUnitController.class);

	private static final long serialVersionUID = -2654487331626546314L;

	@Inject
	private IOrganizationUnitDAO organizationUnitDao;
	
	@Override
	public List<OrganizationUnitBO> getAllOrganizationUnit() {
		List<OrganizationUnitBO> result = organizationUnitDao.findAll().stream().map(OrganizationUnitBO::new).collect(Collectors.toList());
		LOG.info("List of OrganizationUnits");
		return result;
	}

	@Override
	public PaginatedBO<OrganizationUnitBO> paginate(int start, int size) {
		List<OrganizationUnitBO> data = organizationUnitDao.paginate(start, size).stream().map(OrganizationUnitBO::new).collect(Collectors.toList());
		Long count = organizationUnitDao.count();
		PaginatedBO<OrganizationUnitBO> result = new PaginatedBO<>(count, data);
		LOG.info("Paginate OrganizationUnits");
		return result;
	}

	public PaginatedBO<OrganizationUnitBO> findByField(String field, String match) {
		List<OrganizationUnitBO> data = organizationUnitDao.findByField(field, match).stream().map(OrganizationUnitBO::new).collect(Collectors.toList());
		Long count = (long) data.size();
		PaginatedBO<OrganizationUnitBO> result = new PaginatedBO<>(count, data);
		LOG.info("Paginate with field OrganizationUnit");
		return result;
	}

	@Override
	public void updateOrganizationUnit(OrganizationUnitBO organizationUnitBO) {
		OrganizationUnit ou = organizationUnitDao.findById(organizationUnitBO.getId());
		ou.setName(organizationUnitBO.getName());
		ou.setDescription(organizationUnitBO.getDescription());
		organizationUnitDao.save(ou);
		LOG.info("Object OrganizationUnit updated");
	}

	@Override
	public void createOrganizationUnit(OrganizationUnitBO organizationUnitBO) {
		OrganizationUnit ou = organizationUnitBO.toEntity();
		organizationUnitDao.create(ou);
		LOG.info("Object OrganizationUnit created");
	}

	@Override
	public void removeOrganizationUnit(OrganizationUnitBO organizationUnitBO) {
		OrganizationUnit ou = organizationUnitBO.toEntity();
		organizationUnitDao.remove(ou);
		LOG.info("Object OrganizationUnit deleted");
	}

	@Override
	public OrganizationUnitBO findById(Long id) {
		return new OrganizationUnitBO(organizationUnitDao.findById(id), true);

	}
	
	@Override
	public PaginatedBO<OrganizationUnitBO> search(int start, int size, String match, String searchBy) {
		String queryAtributes = prepareSearch(searchBy);	
		List<OrganizationUnitBO> data = organizationUnitDao.search(start, size, match.toLowerCase(), queryAtributes).stream().map(OrganizationUnitBO::new).collect(Collectors.toList());
		Long count = (long) organizationUnitDao.countSearch(match.toLowerCase(), queryAtributes);
		PaginatedBO<OrganizationUnitBO> result = new PaginatedBO<>(count, data);
		LOG.info("Object PaginatedBO<OrganizationUnitBO> using search found");
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