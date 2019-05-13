package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import es.juntadeandalucia.sigc.hc.persistence.dao.IRoleDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.Role;

@Stateless
public class RoleDAO extends GenericDAO<Role, Integer> implements IRoleDAO {

	private static final long serialVersionUID = -8357516942414020993L;
	private static final Log LOG = LogFactory.getLog(Role.class);

	public Role findById(Integer id) {
		Role role;
		try {
			role = getEntityManager().createQuery("select r from Role r where r.id = ?", Role.class)
					.setParameter(1, new Long(id)).getSingleResult();
			Hibernate.initialize(role.getPermissions());
			LOG.info("Object Role with id=" + id + " found");
		} catch (NoResultException e) {
			LOG.error("No Role found by that credential", e);
			role = null;
		}

		return role;
	}

	public List<Role> findAllEager() {
		List<Role> roles = super.findAll();
		roles.stream().forEach(role -> Hibernate.initialize(role.getPermissions()));
		return roles;
	}
}
