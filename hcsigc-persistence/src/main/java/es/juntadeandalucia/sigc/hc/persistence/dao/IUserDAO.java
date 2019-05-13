package es.juntadeandalucia.sigc.hc.persistence.dao;

import java.util.List;

import es.juntadeandalucia.sigc.hc.persistence.entity.User;
import es.juntadeandalucia.sigc.hc.persistence.entity.UserOUPermission;

public interface IUserDAO extends IGenericDAO<User, Long> {
	/**
	 * This method finds by user and password. This method is used for
	 * loggin an user
	 * 
	 * @param username of the user
	 * @param password of the user
	 * 
	 * @return the User entity witch matches with that credentials
	 */
	User findById(Long id);
	User findByNameAndPassword(String username, String password);
	List<User> usersFromGroupId(Long groupId);
	void removePermission(UserOUPermission entity);
}
