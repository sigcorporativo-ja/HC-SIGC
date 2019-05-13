package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import es.juntadeandalucia.sigc.hc.persistence.dao.IUserDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.User;
import es.juntadeandalucia.sigc.hc.persistence.entity.UserOUPermission;

@Stateless
public class UserDAO extends GenericDAO<User, Long> implements IUserDAO {

	private static final Log LOG = LogFactory.getLog(UserDAO.class);

	private static final long serialVersionUID = 1845303987755358773L;

	@Override
	public User findByNameAndPassword(String username, String password) {
		User user;

		try {
			user = getEntityManager()
					.createQuery("select u from User u where u.username = ?", User.class)
                    .setParameter(1, username).getSingleResult();
			if(user.getPassword().equals(password)) {
				Hibernate.initialize(user.getOuPermissions());
				LOG.info("Object User by name and password found");
			}else {
				LOG.error("User found but the password is incorrect");
				user = null;
			}
		} catch (NoResultException e) {
			LOG.error("No user found by that credentials", e);
			user = null;
		}

		return user;
	}

	@Override
	public User findById(Long id) {
		User user;

		try {
			user = getEntityManager().createQuery("select u from User u where u.id = ?", User.class).setParameter(1, id)
					.getSingleResult();
			Hibernate.initialize(user.getOuPermissions());
			Hibernate.initialize(user.getGroupsUsers());
			Hibernate.initialize(user.getDatasets());
			LOG.info("Object User whith id=" + id + " found");
		} catch (NoResultException e) {
			LOG.error("No user found by that credentials", e);
			user = null;
		}

		return user;
	}

	@Override
	public List<User> usersFromGroupId(Long groupId) {
		List<User> users = new ArrayList<>();
		
		try {
			users = getEntityManager()
					.createQuery("select gu.user from GroupUser gu where gu.group.id = ?", User.class)
					.setParameter(1, groupId).getResultList();
			LOG.info("List of Users whith groupId=" + groupId + " found");
		} catch (NoResultException e) {
			LOG.error("No users found by that group ID", e);
			users = null;
		}
		
		return users;
	}

	@Override
	public void removePermission(UserOUPermission entity) {
		entity = getEntityManager().getReference(UserOUPermission.class, entity.getId());
		if (entity != null) {
			getEntityManager().remove(entity);
		}
	}
}
