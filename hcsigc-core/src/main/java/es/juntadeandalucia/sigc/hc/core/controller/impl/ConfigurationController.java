package es.juntadeandalucia.sigc.hc.core.controller.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.juntadeandalucia.sigc.hc.core.bo.ConfigurationBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.controller.IConfigurationController;
import es.juntadeandalucia.sigc.hc.persistence.dao.IConfigurationDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.Configuration;

@Transactional
public class ConfigurationController implements IConfigurationController {

	private static final Log LOG = LogFactory.getLog(ConfigurationController.class);
	
	private static final long serialVersionUID = -1083052405629532292L;

	@Inject
	private IConfigurationDAO configurationDao;

	@Override
	public List<ConfigurationBO> getAllConfiguration() {
		List<ConfigurationBO> result = configurationDao.findAll().stream().map(ConfigurationBO::new).collect(Collectors.toList());
		LOG.info("List of Configurations");
		return result;
	}

	@Override
	public PaginatedBO<ConfigurationBO> paginate(int start, int size) {
		List<ConfigurationBO> data = configurationDao.paginate(start, size).stream().map(ConfigurationBO::new)
				.collect(Collectors.toList());
		Long count = configurationDao.count();
		PaginatedBO<ConfigurationBO> result = new PaginatedBO<>(count, data);
		LOG.info("Paginate Configurations");
		return result;
	}

	@Override
	public void updateConfiguration(ConfigurationBO configurationBO) {
		Configuration config = configurationDao.findById(configurationBO.getId());
		config.setKey(configurationBO.getKey());
		config.setValue(configurationBO.getValue());
		config.setDescription(configurationBO.getDescription());
		configurationDao.save(config);
		LOG.info("Object Configuration updated");
	}

	@Override
	public void createConfiguration(ConfigurationBO configurationBO) {
		Configuration config = configurationBO.toEntity();
		configurationDao.create(config);
		LOG.info("Object Configuration created");
	}

	@Override
	public void removeConfiguration(ConfigurationBO configurationBO) {
		Configuration config = configurationBO.toEntity();
		configurationDao.remove(config);
		LOG.info("Object Configuration deleted");
	}
	
	@Override
	public ConfigurationBO findById(Long id) {
		return new ConfigurationBO(configurationDao.findById(id), true);
	}

	@Override
	public PaginatedBO<ConfigurationBO> findByField(String field, String match) {
		List<ConfigurationBO> data = configurationDao.findByField(field, match).stream().map(ConfigurationBO::new).collect(Collectors.toList());
		Long count = (long) data.size();
		PaginatedBO<ConfigurationBO> result = new PaginatedBO<>(count, data);
		LOG.info("Paginate with field Configurations");
		return result;
	}
	
	@Override
	public PaginatedBO<ConfigurationBO> search(int start, int size, String match, String searchBy) {
		String queryAtributes = prepareSearch(searchBy);
		
		List<ConfigurationBO> data = configurationDao.search(start, size, match.toLowerCase(), queryAtributes).stream().map(ConfigurationBO::new).collect(Collectors.toList());
		Long count = (long) configurationDao.countSearch(match.toLowerCase(), queryAtributes);
		PaginatedBO<ConfigurationBO> result = new PaginatedBO<>(count, data);
		LOG.info("Object PaginatedBO<ConfigurationBO> using search found");
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