package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import es.juntadeandalucia.sigc.hc.persistence.dao.IDBMSDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.DBMS;

@Stateless
public class DBMSDAO extends GenericDAO<DBMS, Long> implements IDBMSDAO {

	private static final Log LOG = LogFactory.getLog(DBMSDAO.class);

	private static final long serialVersionUID = -7249186325663068382L;

	@Override
	public DBMS findById(Long id) {
		DBMS dbms;

		try {
			dbms = getEntityManager().createQuery("select d from DBMS d where d.id = ?", DBMS.class)
					.setParameter(1, id).getSingleResult();
			Hibernate.initialize(dbms.getDbConnections());
			LOG.info("Object DBMS whith id=" + id + " found");
		}
		catch(NoResultException e) {
			LOG.error("No DBMS found by that credential", e);
			dbms = null;
		}

		return dbms;
	}
}
