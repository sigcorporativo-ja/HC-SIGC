package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.juntadeandalucia.sigc.hc.persistence.dao.IConfigurationDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.Configuration;

@Stateless
public class ConfigurationDAO extends GenericDAO<Configuration, Long> implements IConfigurationDAO {

	private static final Log LOG = LogFactory.getLog(DBMSDAO.class);

	private static final long serialVersionUID = -5807093313999661679L;

	@Override
	public Configuration findById(Long id) {
		Configuration configuration;

		try {
			configuration = getEntityManager().createQuery("select c from Configuration c where c.id = ?", Configuration.class)
					.setParameter(1, id).getSingleResult();
			LOG.info("Object Configuration whith id=" + id + " found");
		}
		catch(NoResultException e) {
			LOG.error("No Configuration found by that credential", e);
			configuration = null;
		}

		return configuration;
	}

}
