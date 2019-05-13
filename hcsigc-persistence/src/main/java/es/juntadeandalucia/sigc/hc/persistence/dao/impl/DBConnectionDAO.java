package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import es.juntadeandalucia.sigc.hc.persistence.dao.IDBConnectionDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.DBConnection;

@Stateless
public class DBConnectionDAO extends GenericDAO<DBConnection, Long> implements IDBConnectionDAO {

	private static final Log LOG = LogFactory.getLog(DBConnectionDAO.class);

	private static final long serialVersionUID = 8650667065695896205L;

	@Override
	public DBConnection findById(Long id) {
		DBConnection dbConnection;

		try {
			dbConnection = getEntityManager().createQuery("select d from DBConnection d where d.id = ?", DBConnection.class)
					.setParameter(1, id).getSingleResult();
			Hibernate.initialize(dbConnection.getDatasets());
			Hibernate.initialize(dbConnection.getGroups());
			Hibernate.initialize(dbConnection.getDbms());
			LOG.info("Object DBConnection whith id=" + id + " found");
		}
		catch(NoResultException e) {
			LOG.error("No DBConnection found by that credential", e);
			dbConnection = null;
		}

		return dbConnection;
	}
	
	@Override
	public List<DBConnection> paginateByOrganizationUnit(int start, int size, String ou) {
		List<DBConnection> result = new ArrayList<>();
		
		try {
			result = getEntityManager()
					.createQuery("select e from " + getPersistentClass().getSimpleName() + " e join e.groups g where g.organizationUnit.name = ?1", getPersistentClass())
					.setParameter(1, ou)
					.setFirstResult(start).setMaxResults(size)
					.getResultList();
			LOG.info("List of " + getPersistentClass().getSimpleName() + "s with organizationUnit name=" + ou + " found");
		} catch (NoResultException e) {
			LOG.error("No " + getPersistentClass().getSimpleName() + "s found by that organizationUnit name=" + ou, e);
			result = null;
		}
		
		return result;
	}
	
	@Override
	public Long countByOrganizationUnit(String ou) {
		Long result = getEntityManager()
				.createQuery("select count(e) from " + getPersistentClass().getSimpleName() + " e join e.groups g where g.organizationUnit.name = ?1", Long.class)
				.setParameter(1, ou)
				.getSingleResult();
		return result;
	}
	
	@Override
	public List<DBConnection> searchByOrganizationUnit(int start, int size, String match, String queryAtributes, String ou) {
		List<DBConnection> result = new ArrayList<>();
		
		try {
			result = getEntityManager()
					.createQuery("select e from " + getPersistentClass().getSimpleName() + " e join e.groups g where (" + queryAtributes + ") and g.organizationUnit.name = ?2", getPersistentClass())
					.setParameter(1, "%" + match + "%" )
					.setParameter(2, ou)
					.setFirstResult(start).setMaxResults(size).getResultList();
			LOG.info("List of " + getPersistentClass().getSimpleName() + "s with permissions and match=" + match + " found");
		} catch (NoResultException e) {
			LOG.error("No " + getPersistentClass().getSimpleName() + "s with permissions found by that match=" + match, e);
			result = null;
		}
		
		return result;
	}
	
	@Override
	public Long countSearchByOrganizationUnit(String match, String queryAtributes, String ou) {
		Long result = getEntityManager()
				.createQuery("select count(e) from " + getPersistentClass().getSimpleName() + " e join e.groups g where (" + queryAtributes + ") and g.organizationUnit.name = ?2", Long.class)
				.setParameter(1, "%" + match + "%" )
				.setParameter(2, ou)
				.getSingleResult();
		return result;
	}
	
}
