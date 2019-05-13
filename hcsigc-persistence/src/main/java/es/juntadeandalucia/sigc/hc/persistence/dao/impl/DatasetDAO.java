package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import es.juntadeandalucia.sigc.hc.persistence.dao.IDatasetDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.Dataset;
import es.juntadeandalucia.sigc.hc.persistence.entity.TempTableRegistry;

@Stateless
public class DatasetDAO extends GenericDAO<Dataset, Long> implements IDatasetDAO {
	private static final Log LOG = LogFactory.getLog(DatasetDAO.class);
	private static final long serialVersionUID = 6762248694516270776L;

	@Override
	public Dataset findById(Long id) {
		Dataset dataset;

		try {
			dataset = getEntityManager().createQuery("select d from Dataset d where d.id = ?", Dataset.class)
					.setParameter(1, id).getSingleResult();
			Hibernate.initialize(dataset.getRemoteConnection());
			Hibernate.initialize(dataset.getUser());
			Hibernate.initialize(dataset.getDbConnection());
			Hibernate.initialize(dataset.getDataType());
			Hibernate.initialize(dataset.getGroupsUsers());
			LOG.info("Object Dataset whith id=" + id + " found");
		} catch (NoResultException e) {
			LOG.error("No Dataset found by that credential", e);
			dataset = null;
		}

		return dataset;
	}

	@Override
	public List<Dataset> paginate(int start, int size, String queryChecks, String querySorts, Timestamp queryStartDate,
			Timestamp queryEndDate) {
		List<Dataset> result = new ArrayList<>();
		String join = "";
		String where = "";
		String action = "";

		if (!queryChecks.isEmpty()) {
			where = " where ";
			if (queryChecks.contains("gu.user.id")) {
				join = " left join e.groupsUsers gu join e.user u ";
				queryChecks = queryChecks.replace("e.user.id", "u.id");
			}
		}

		String order = "";
		if (!querySorts.isEmpty()) {
			order = " ORDER BY " + querySorts + " desc";
		}

		String between = "";
		if (queryStartDate != null && queryEndDate != null) {
			between = "e.creationDate between " + "'" + queryStartDate + "'" + " and " + "'" + queryEndDate + "'";
			if (queryChecks.isEmpty())
				action = " where ";
			else
				action = " and ";
		}

		try {
			result = getEntityManager()
					.createQuery("select distinct(e) from " + getPersistentClass().getSimpleName() + " e" + join + where + queryChecks
							+ action + between + order, getPersistentClass())
					.setFirstResult(start).setMaxResults(size).getResultList();
			LOG.info("List of " + getPersistentClass().getSimpleName() + "s found");
			LOG.info(result);
		} catch (NoResultException e) {
			LOG.error("No " + getPersistentClass().getSimpleName() + "s found", e);
			result = null;
		}

		return result;
	}

	@Override
	public Long count(String queryChecks, Timestamp queryStartDate, Timestamp queryEndDate) {
		String join = "";
		String where = "";
		String action = "";

		if (!queryChecks.isEmpty()) {
			where = " where ";
			if (queryChecks.contains("gu.user.id")) {
				join = " join e.user u left join e.groupsUsers gu ";
				queryChecks = queryChecks.replace("e.user.id", "u.id");
			}
		}

		String between = "";
		if (queryStartDate != null && queryEndDate != null) {
			between = "e.creationDate between " + "'" + queryStartDate + "'" + " and " + "'" + queryEndDate + "'";
			if (queryChecks.isEmpty())
				action = " where ";
			else
				action = " and ";
		}

		Long result = getEntityManager().createQuery(
				"select count(distinct e) from " + getPersistentClass().getSimpleName() + " e" + join + where + queryChecks + action + between, Long.class)
				.getSingleResult();
		return result;
	}

	@Override
	public List<Dataset> search(int start, int size, String match, String queryAtributes, String queryChecks,
			String querySorts, Timestamp queryStartDate, Timestamp queryEndDate) {
		List<Dataset> result = new ArrayList<>();
		String and1 = "";
		String and2 = "";
		String order = "";
		String join = "";
		if (!queryChecks.isEmpty()) {
			and1 = ") and (";
			if (queryChecks.contains("gu.user.id")) {
				join = " join e.user u left join e.groupsUsers gu ";
				queryChecks = queryChecks.replace("e.user.id", "u.id");
			}
		}

		if (!querySorts.isEmpty()) {
			order = " ORDER BY " + querySorts + " desc";
		}

		String between = "";
		if (queryStartDate != null && queryEndDate != null) {
			between = "e.creationDate between " + "'" + queryStartDate + "'" + " and " + "'" + queryEndDate + "'";
			and2 = " and ";
		}

		try {
			result = getEntityManager()
					.createQuery("select distinct(e) from " + getPersistentClass().getSimpleName() + " e" + join + " where (" + queryAtributes
							+ and1 + queryChecks + ")" + and2 + between + order, getPersistentClass())
					.setParameter(1, "%" + match + "%").setFirstResult(start).setMaxResults(size).getResultList();
			LOG.info("List of " + getPersistentClass().getSimpleName() + "s with match=" + match + " found");
			LOG.info(result);
		} catch (NoResultException e) {
			LOG.error("No " + getPersistentClass().getSimpleName() + "s found by that match=" + match, e);
			result = null;
		}

		return result;
	}

	@Override
	public Long countSearch(String match, String queryAtributes, String queryChecks, Timestamp queryStarDate,
			Timestamp queryEndDate) {
		String and = "";
		String and2 = "";
		String join = "";

		if (!queryChecks.isEmpty()) {
			and = ") and (";
			if (queryChecks.contains("gu.user.id")) {
				join = " join e.user u left join e.groupsUsers gu ";
				queryChecks = queryChecks.replace("e.user.id", "u.id");
			}
		}

		String between = "";
		if (queryStarDate != null && queryEndDate != null) {
			between = "e.creationDate between " + "'" + queryStarDate + "'" + " and " + "'" + queryEndDate + "'";
			and2 = " and ";
		}

		Long result = getEntityManager()
				.createQuery("select count(distinct e) from " + getPersistentClass().getSimpleName() + " e" + join + " where ("
						+ queryAtributes + and + queryChecks + ")" + and2 + between, Long.class)
				.setParameter(1, "%" + match + "%").getSingleResult();
		return result;
	}

	public String createTempTable(String name, Iterable<String> attributeNames) {
		String uniqueTableName = name.concat("_").concat(UUID.randomUUID().toString().replace("-", ""));
		StringBuilder createTableSQL = new StringBuilder();
		
		uniqueTableName = uniqueTableName.replace(" ", "_");
		createTableSQL.append("CREATE TABLE IF NOT EXISTS ").append(uniqueTableName).append(" (");

		boolean firstElem = true;
		for (String attributeName : attributeNames) {
			if (firstElem) {
				firstElem = false;
			} else {
				createTableSQL.append(",");
			}
			createTableSQL.append(attributeName).append(" text");
		}
		createTableSQL.append(");");

		EntityManager em = getDataEntityManager();
		Session session = em.unwrap(Session.class);
		session.createSQLQuery(createTableSQL.toString()).executeUpdate();

		TempTableRegistry ttreg = new TempTableRegistry();
		ttreg.setName(uniqueTableName);

		em.persist(ttreg);

		return uniqueTableName + "," + ttreg.getId().toString();
	}

	@Override
	public void insertBatch(String tableName, Collection<? extends Iterable<String>> batch) {
		StringBuilder insertBatchSQL = new StringBuilder();

		for (Iterable<String> row : batch) {
			insertBatchSQL.append("INSERT INTO ").append(tableName).append(" VALUES (");

			boolean firstElem = true;
			for (String value : row) {
				if (firstElem) {
					firstElem = false;
				} else {
					insertBatchSQL.append(",");
				}
				insertBatchSQL.append("'").append(value).append("'");
			}
			insertBatchSQL.append(");").append("\n");
		}

		Session session = getDataEntityManager().unwrap(Session.class);
		session.createSQLQuery(insertBatchSQL.toString()).executeUpdate();
	}

	@Override
	public void removeBatchDataUpload(long id) {
		StringBuilder deleteBatchSQL = new StringBuilder();
		deleteBatchSQL.append("DELETE FROM ").append("temp_tables").append(" WHERE x_ttre=" + id);
		Session session = getDataEntityManager().unwrap(Session.class);
		session.createSQLQuery(deleteBatchSQL.toString()).executeUpdate();
		
		TempTableRegistry ttreg = new TempTableRegistry();
		ttreg.setName("temp_tables");

		getDataEntityManager().remove(ttreg);
	}
	
	@Override
	public void removeDataUpload(long id) {
		TempTableRegistry ttr = getDataEntityManager().find(TempTableRegistry.class, id);
		String nameTempTable = ttr.getName();
		
		StringBuilder deleteBatchSQL = new StringBuilder();
		deleteBatchSQL.append("DROP TABLE ").append(nameTempTable);
		Session session = getDataEntityManager().unwrap(Session.class);
		session.createSQLQuery(deleteBatchSQL.toString()).executeUpdate();
	}

}
