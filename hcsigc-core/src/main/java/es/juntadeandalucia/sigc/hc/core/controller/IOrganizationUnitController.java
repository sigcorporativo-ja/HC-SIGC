package es.juntadeandalucia.sigc.hc.core.controller;

import java.io.Serializable;
import java.util.List;

import es.juntadeandalucia.sigc.hc.core.bo.OrganizationUnitBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;

public interface IOrganizationUnitController extends Serializable {

	List<OrganizationUnitBO> getAllOrganizationUnit();

	/**
	 * Retrieves Datasets applying pagination
	 *
	 * @param start the index of the first dataset
	 * @param size  of the page
	 *
	 * @return a subset of Datasets
	 */
	public PaginatedBO<OrganizationUnitBO> paginate(int start, int size);

	OrganizationUnitBO findById(Long ouId);

	void createOrganizationUnit(OrganizationUnitBO ou);

	void updateOrganizationUnit(OrganizationUnitBO ou);

	void removeOrganizationUnit(OrganizationUnitBO ou);

	public PaginatedBO<OrganizationUnitBO> findByField(String field, String match);

	PaginatedBO<OrganizationUnitBO> search(int start, int size, String match, String searchBy);
}
