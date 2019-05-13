package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import es.juntadeandalucia.sigc.hc.persistence.dao.IOrganizationUnitDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.OrganizationUnit;

@Stateless
public class OrganizationUnitDAO extends GenericDAO<OrganizationUnit, Long> implements IOrganizationUnitDAO {
	
	private static final Log LOG = LogFactory.getLog(OrganizationUnitDAO.class);
	
	private static final long serialVersionUID = -8612926894484235675L;

	@Override
	public OrganizationUnit findById(Long id) {
		OrganizationUnit organizationUnit;

		try {
			organizationUnit = getEntityManager()
					.createQuery("select u from OrganizationUnit u where u.id = ?", OrganizationUnit.class)
					.setParameter(1, id).getSingleResult();
			Hibernate.initialize(organizationUnit.getGroups());
			Hibernate.initialize(organizationUnit.getOuPermissions());
			Hibernate.initialize(organizationUnit.getUsers());
			LOG.info("Object organizationUnit whith id=" + id + " found");
		} catch (NoResultException e) {
			LOG.error("No organizationUnit found by that credentials", e);
			organizationUnit = null;
		}

		return organizationUnit;
	}
}
