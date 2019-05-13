/* Copyright 2015 Guadaltel, S.A.
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */
package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.juntadeandalucia.sigc.hc.persistence.dao.IGenericDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.GenericEntity;

public abstract class GenericDAO<T extends GenericEntity, I extends Serializable>
		implements IGenericDAO<T, I>, Serializable {

	private static final Log LOG = LogFactory.getLog(GroupDAO.class);
	
	private static final long serialVersionUID = 4963515534921245288L;

	@PersistenceContext(unitName = "hcsigc")
	private transient EntityManager entityManager;
	
	@PersistenceContext(unitName = "hcsigc_data")
	private transient EntityManager dataEntityManager;

	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
			this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		} else if (getClass().getSuperclass().getGenericSuperclass() instanceof ParameterizedType) {
			this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		} else {
			throw new IllegalStateException();
		}
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public Long count() {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(persistentClass)));
		return entityManager.createQuery(cq).getSingleResult();
	}

	@Override
	public T findById(I id) {
		return (T) getEntityManager().find(getPersistentClass(), id);
	}

	public void lock(T entity) {
		getEntityManager().lock(entity, LockModeType.PESSIMISTIC_WRITE);
	}

	@Override
	public List<T> findAll() {
		return getEntityManager()
				.createQuery("select e from " + getPersistentClass().getSimpleName() + " e", getPersistentClass())
				.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T save(T entity) {
		T syncEntity = findById((I) entity.getId());
		entity.setVersion(syncEntity.getVersion());
		return getEntityManager().merge(entity);
		
	}

	@Override
	public Long create(T entity) {
		entityManager.persist(entity);
		return (Long) entity.getId();
	}

	@Override
	public void remove(T entity) {
		entity = getEntityManager().getReference(getPersistentClass(), entity.getId());
		if (entity != null) {
			getEntityManager().remove(entity);
		}
	}

	@Override
	public List<T> paginate(int start, int size) {
		return getEntityManager()
				.createQuery("select e from " + getPersistentClass().getSimpleName() + " e", getPersistentClass())
				.setFirstResult(start).setMaxResults(size).getResultList();
	}

	public List<T> findByField(String field, String match) {
		return getEntityManager()
				.createQuery("from " + getPersistentClass().getSimpleName() + " WHERE " + field + " LIKE ?",
						getPersistentClass())
				.setParameter(1, "%" + match + "%").getResultList();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public EntityManager getDataEntityManager() {
		return dataEntityManager;
	}

	public void setDataEntityManager(EntityManager entityManager) {
		this.dataEntityManager = entityManager;
	}
	
	@Override
	public List<T> search(int start, int size, String match, String queryAtributes) {
		List<T> result = new ArrayList<>();
		
		try {
			result = getEntityManager()
					.createQuery("select e from " + getPersistentClass().getSimpleName() + " e where " + queryAtributes, getPersistentClass())
					.setParameter(1, "%" + match + "%" )
					.setFirstResult(start).setMaxResults(size).getResultList();
			LOG.info("List of " + getPersistentClass().getSimpleName() + "s with match=" + match + " found");
		} catch (NoResultException e) {
			LOG.error("No " + getPersistentClass().getSimpleName() + "s found by that match=" + match, e);
			result = null;
		}
		
		return result;
	}
	
	@Override
	public Long countSearch(String match, String queryAtributes) {
		Long result = getEntityManager()
				.createQuery("select count(e) from " + getPersistentClass().getSimpleName() + " e where " + queryAtributes, Long.class)
				.setParameter(1, "%" + match + "%" )
				.getSingleResult();
		return result;
	}
	
	@Override
	public List<T> paginateByOrganizationUnit(int start, int size, String ou) {
		List<T> result = new ArrayList<>();
		
		try {
			result = getEntityManager()
					.createQuery("select e from " + getPersistentClass().getSimpleName() + " e where e.organizationUnit.name = ?1", getPersistentClass())
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
				.createQuery("select count(e) from " + getPersistentClass().getSimpleName() + " e where e.organizationUnit.name = ?1", Long.class)
				.setParameter(1, ou)
				.getSingleResult();
		return result;
	}
	
	@Override
	public List<T> searchByOrganizationUnit(int start, int size, String match, String queryAtributes, String ou) {
		List<T> result = new ArrayList<>();
		
		try {
			result = getEntityManager()
					.createQuery("select e from " + getPersistentClass().getSimpleName() + " e where (" + queryAtributes + ") and e.organizationUnit.name = ?2", getPersistentClass())
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
				.createQuery("select count(e) from " + getPersistentClass().getSimpleName() + " e where (" + queryAtributes + ") and e.organizationUnit.name = ?2", Long.class)
				.setParameter(1, "%" + match + "%" )
				.setParameter(2, ou)
				.getSingleResult();
		return result;
	}

}
