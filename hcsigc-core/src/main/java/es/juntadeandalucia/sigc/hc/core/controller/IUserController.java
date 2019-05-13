package es.juntadeandalucia.sigc.hc.core.controller;

import java.io.Serializable;
import java.util.List;

import es.juntadeandalucia.sigc.hc.core.bo.PermissionBO;
import es.juntadeandalucia.sigc.hc.core.bo.UserBO;
import es.juntadeandalucia.sigc.hc.core.bo.UserPaginatedBO;

public interface IUserController extends Serializable {

	List<UserBO> getAllUser();

	/**
	 * Retrieves Users applying pagination
	 *
	 * @param start the index of the first user
	 * @param size  of the page
	 *
	 * @return a subset of Users
	 */
	public UserPaginatedBO paginate(int start, int size);

	void createUser(UserBO userBO);

	void updateUser(UserBO userBO);

	void removeUser(UserBO userBO);

	/**
	 * This method checks if an user has all the permissions specified
	 *
	 * @param user             who must have all the permissions
	 * @param permissionsCodes codes of the permissions
	 *
	 * @return true if the user has all the specified permissions
	 */
	boolean hasPermissions(UserBO user, String[] permissionsCodes);

	UserBO findById(Long id);

	List<UserBO> findByUsersId(List<UserBO> users);

	UserBO findByNameAndPassword(String username, String password);

	public UserPaginatedBO findByField(String field, String match);

	List<UserBO> usersFromGroupId(Long groupId);

	UserPaginatedBO search(int start, int size, String searchBy, String match);
	
	List<PermissionBO> getUserPermissionsGrant(Long id);
	
	/**
	 * This method returns the user logged as
	 * UserBO instance
	 * 
	 * @return user logged in session. Null in other case.
	 */
	UserBO getSessionUser();

}