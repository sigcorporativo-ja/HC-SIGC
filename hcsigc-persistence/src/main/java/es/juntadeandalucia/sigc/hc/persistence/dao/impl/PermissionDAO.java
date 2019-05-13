package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import es.juntadeandalucia.sigc.hc.persistence.dao.IPermissionDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.Permission;
import es.juntadeandalucia.sigc.hc.persistence.entity.User;

@Stateless
public class PermissionDAO extends GenericDAO<Permission, Long> implements IPermissionDAO {

	private static final long serialVersionUID = -7211418161569260536L;
	private static final Log LOG = LogFactory.getLog(PermissionDAO.class);

	public Permission findById(Long id) {
		Permission permission;
		try {
			permission = getEntityManager().createQuery("select u from Permission u where u.id = ?", Permission.class)
					.setParameter(1, id).getSingleResult();
			Hibernate.initialize(permission.getRoles());
			Hibernate.initialize(permission.getPermissionsGranted());
			Hibernate.initialize(permission.getPermissionsRequired());
			Hibernate.initialize(permission.getOuPermissions());
		} catch (NoResultException e) {
			LOG.error("No user found by that credentials", e);
			permission = null;
		}
		return permission;
	}
}
