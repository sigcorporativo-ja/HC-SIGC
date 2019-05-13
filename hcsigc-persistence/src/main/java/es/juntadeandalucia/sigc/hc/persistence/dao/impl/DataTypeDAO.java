package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import es.juntadeandalucia.sigc.hc.persistence.dao.IDataTypeDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.DataType;

@Stateless
public class DataTypeDAO extends GenericDAO<DataType, Long> implements IDataTypeDAO {

	private static final Log LOG = LogFactory.getLog(DataTypeDAO.class);
	
	private static final long serialVersionUID = -7622909904995698868L;
	
	@Override
	public DataType findById(Long id) {
		DataType dataType;

		try {
			dataType = getEntityManager().createQuery("select d from DataType d where d.id = ?", DataType.class)
					.setParameter(1, id).getSingleResult();
			Hibernate.initialize(dataType.getDatasets());
			LOG.info("Object dataType whith id=" + id + " found");
		} catch (NoResultException e) {
			LOG.error("No dataType found by that credentials", e);
			dataType = null;
		}

		return dataType;
	}
}
