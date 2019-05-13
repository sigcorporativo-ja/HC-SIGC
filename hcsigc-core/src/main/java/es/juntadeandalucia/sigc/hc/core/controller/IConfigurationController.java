package es.juntadeandalucia.sigc.hc.core.controller;

import java.io.Serializable;
import java.util.List;

import es.juntadeandalucia.sigc.hc.core.bo.ConfigurationBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;

public interface IConfigurationController extends Serializable {

	List<ConfigurationBO> getAllConfiguration();

	/**
	 * Retrieves Configuration applying pagination
	 *
	 * @param start the index of the first Configuration
	 * @param size  of the page
	 *
	 * @return a subset of Configurations
	 */
	public PaginatedBO<ConfigurationBO> paginate(int start, int size);

	ConfigurationBO findById(Long id);

	void createConfiguration(ConfigurationBO config);

	void updateConfiguration(ConfigurationBO config);

	void removeConfiguration(ConfigurationBO config);

	public PaginatedBO<ConfigurationBO> findByField(String field, String match);
	
	PaginatedBO<ConfigurationBO> search(int start, int size, String match, String searchBy);
}