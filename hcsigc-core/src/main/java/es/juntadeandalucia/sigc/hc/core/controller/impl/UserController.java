package es.juntadeandalucia.sigc.hc.core.controller.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.picketlink.Identity;

import es.juntadeandalucia.sigc.hc.core.bo.PermissionBO;
import es.juntadeandalucia.sigc.hc.core.bo.UserBO;
import es.juntadeandalucia.sigc.hc.core.bo.UserPaginatedBO;
import es.juntadeandalucia.sigc.hc.core.controller.IUserController;
import es.juntadeandalucia.sigc.hc.persistence.dao.IDatasetDAO;
import es.juntadeandalucia.sigc.hc.persistence.dao.IGroupDAO;
import es.juntadeandalucia.sigc.hc.persistence.dao.IOrganizationUnitDAO;
import es.juntadeandalucia.sigc.hc.persistence.dao.IPermissionDAO;
import es.juntadeandalucia.sigc.hc.persistence.dao.IUserDAO;
import es.juntadeandalucia.sigc.hc.persistence.dao.IUserOUPermissionDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.OrganizationUnit;
import es.juntadeandalucia.sigc.hc.persistence.entity.Permission;
import es.juntadeandalucia.sigc.hc.persistence.entity.User;
import es.juntadeandalucia.sigc.hc.persistence.entity.UserOUPermission;

public class UserController implements IUserController {

	private static final Log LOG = LogFactory.getLog(UserController.class);

	private static final long serialVersionUID = 2733546077693493683L;

	@Inject
	private IUserDAO userDao;

	@Inject
	private IOrganizationUnitDAO ouDao;

	@Inject
	private IGroupDAO groupDao;

	@Inject
	private IDatasetDAO datasetDao;

	@Inject
	private IPermissionDAO permissionDAO;

	@Inject
	private IUserOUPermissionDAO userOUPermissionDAO;

	@Inject
	private Identity identity;

	@Override
	public List<UserBO> getAllUser() {
		List<UserBO> result = userDao.findAll().stream().map(UserBO::new).collect(Collectors.toList());
		LOG.info("List of Users");
		return result;
	}

	@Override
	public UserPaginatedBO paginate(int start, int size) {
		String[] uoPermissions = { "ADMIN_UO" };
		UserBO sessionUser = getSessionUser();

		List<UserBO> data = new ArrayList<>();
		Long count = null;
		if (!hasPermissions(sessionUser, uoPermissions)) {
			data = userDao.paginateByOrganizationUnit(start, size, sessionUser.getOrganizationUnit().getName()).stream()
					.map(UserBO::new).collect(Collectors.toList());
			count = userDao.countByOrganizationUnit(sessionUser.getOrganizationUnit().getName());
		} else {
			data = userDao.paginate(start, size).stream().map(UserBO::new).collect(Collectors.toList());
			count = userDao.count();
		}

		UserPaginatedBO result = new UserPaginatedBO(count, data);
		LOG.info("Obeject UserPaginated found");
		return result;
	}

	@Override
	public void updateUser(UserBO userBO) {
		String[] uoPermissions = { "ADMIN_UO" };
		UserBO sessionUser = getSessionUser();

		if (hasPermissions(sessionUser, uoPermissions)
				|| userBO.getOrganizationUnit().getId().equals(sessionUser.getOrganizationUnit().getId())) {

			User user = userDao.findById(Long.valueOf(userBO.getId()));
			user.setName(userBO.getName());
			user.setSurname(userBO.getSurname());
			user.setMail(userBO.getMail());
			user.setUsername(userBO.getUsername());
			if (!(userBO.getPassword() == null || userBO.getPassword().isEmpty())) {
				user.setPassword(userBO.getPassword());
			}

			user.setQuota(userBO.getQuota());
			OrganizationUnit ou = ouDao.findById(userBO.getOrganizationUnit().getId());
			user.setOrganizationUnit(ou);

			// Firstly, remove the permissions
			user.getOuPermissions().stream().forEach(x -> {
				userDao.removePermission(x);
			});

			List<UserOUPermission> oUPermissions = new ArrayList<>();
			List<PermissionBO> permissionsGrant = getUserPermissionsGrant(Long.valueOf(sessionUser.getId()));
			for (PermissionBO permissionBO : userBO.getPermissions()) {
				UserOUPermission userOUPermission = new UserOUPermission();

				userOUPermission.setOu(ou);
				Permission permission = permissionDAO.findById(permissionBO.getId());
				userOUPermission.setPermission(permission);
				userOUPermission.setUser(user);
				if (permissionsGrant.contains(permissionBO)) {
					oUPermissions.add(userOUPermission);
				}
			}

			user.setOuPermissions(oUPermissions);
			userDao.save(user);

			LOG.info("Object User updated");
		} else {
			LOG.info("Remove object User denegated");
		}
	}

	@Override
	public void createUser(UserBO userBO) {
		String[] uoPermissions = { "ADMIN_UO" };
		UserBO sessionUser = getSessionUser();

		if (hasPermissions(sessionUser, uoPermissions)
				|| userBO.getOrganizationUnit().getId().equals(sessionUser.getOrganizationUnit().getId())) {
			User user = userBO.toEntity();
			OrganizationUnit ou = ouDao.findById((Long) userBO.getOrganizationUnit().getId());
			user.setOrganizationUnit(ou);
			userDao.create(user);
			LOG.info("Object User created");
		} else {
			LOG.info("Create object User denegated");
		}
	}

	@Override
	public void removeUser(UserBO userBO) {
		String[] uoPermissions = { "ADMIN_UO" };
		UserBO sessionUser = getSessionUser();

		if (hasPermissions(sessionUser, uoPermissions)
				|| userBO.getOrganizationUnit().getId().equals(sessionUser.getOrganizationUnit().getId())) {
			User user = userDao.findById(Long.valueOf(userBO.getId()));

			// Firstly, remove the permissions
			user.getOuPermissions().stream().forEach(x -> {
				userDao.removePermission(x);
			});

			// Secondly, remove the relation with groups
			user.getGroupsUsers().stream().forEach(x -> {
				groupDao.removeGroupUser(x);
			});

			// Thirdly, update the relation with dataset and associate this whit the admin
			user.getDatasets().stream().forEach(x -> {
				x.setUser(sessionUser.toEntity());
				datasetDao.save(x);
			});

			userDao.remove(user);
			LOG.info("Object User deleted");
		} else {
			LOG.info("Remove object User denegated");
		}
	}

	@Override
	public boolean hasPermissions(UserBO user, String[] permissionsCodes) {
		boolean hasPermissions = false;
		String permissionsLog = "";

		if (user == null) {
			if (permissionsCodes.length == 1 && permissionsCodes[0].equals("VIEW_DATA")) {
				hasPermissions = true;
				LOG.info("User anonymous has Permissions :VIEW_DATA");
			} else {
				LOG.info("User anonymous does not have Permissions");
			}
		} else {
			List<PermissionBO> userPerms = user.getPermissions();
			for (int i = 0; i < permissionsCodes.length; i++) {
				String permCode = permissionsCodes[i];
				permissionsLog += ":" + permCode;
				for (int j = 0; j < userPerms.size(); j++) {
					PermissionBO userPerm = userPerms.get(j);
					boolean has = permCode.equals(userPerm.getCode());
					if (has)
						hasPermissions = has;
				}
				if (!hasPermissions)
					break;
			}

			if (hasPermissions) {
				LOG.info("User with id=" + user.getId() + " has Permissions" + permissionsLog);
			} else {
				LOG.info("User with id=" + user.getId() + " does not have Permissions" + permissionsLog);
			}
		}

		return hasPermissions;
	}

	@Override
	public UserBO findById(Long id) {
		return new UserBO(userDao.findById(id), true);
	}

	@Override
	public UserBO findByNameAndPassword(String username, String password) {
		UserBO userBo;

		User user = userDao.findByNameAndPassword(username, password);
		if (user != null) {
			userBo = new UserBO(user, true);
		} else {
			userBo = null;
		}

		return userBo;
	}

	@Override
	public UserPaginatedBO findByField(String field, String match) {
		List<UserBO> data = userDao.findByField(field, match).stream().map(UserBO::new).collect(Collectors.toList());
		Long count = (long) data.size();
		UserPaginatedBO result = new UserPaginatedBO(count, data);
		LOG.info("Object UserPaginated whith field found");
		return result;
	}

	@Override
	public List<UserBO> findByUsersId(List<UserBO> users) {
		List<UserBO> ret = new ArrayList<>();
		for (UserBO user : users) {
			ret.add(findById(Long.parseLong(user.getId())));
		}
		LOG.info("List of Users by usersId found");
		return ret;
	}

	@Override
	public List<UserBO> usersFromGroupId(Long groupId) {
		return userDao.usersFromGroupId(groupId).stream().map(UserBO::new).collect(Collectors.toList());
	}

	@Override
	public UserPaginatedBO search(int start, int size, String match, String searchBy) {
		String queryAtributes = prepareSearch(searchBy);

		String[] uoPermissions = { "ADMIN_UO" };
		UserBO sessionUser = getSessionUser();

		List<UserBO> data = new ArrayList<>();
		Long count = null;
		if (!hasPermissions(sessionUser, uoPermissions)) {
			data = userDao
					.searchByOrganizationUnit(start, size, match.toLowerCase(), queryAtributes,
							sessionUser.getOrganizationUnit().getName())
					.stream().map(UserBO::new).collect(Collectors.toList());
			count = (long) userDao.countSearchByOrganizationUnit(match.toLowerCase(), queryAtributes,
					sessionUser.getOrganizationUnit().getName());
		} else {
			data = userDao.search(start, size, match.toLowerCase(), queryAtributes).stream().map(UserBO::new)
					.collect(Collectors.toList());
			count = (long) userDao.countSearch(match.toLowerCase(), queryAtributes);
		}

		UserPaginatedBO result = new UserPaginatedBO(count, data);
		LOG.info("Object UserPaginatedBO using search found");
		return result;
	}

	private String prepareSearch(String searchBy) {
		String[] arraySearchBy = searchBy.split(",");

		// Create the query with the attributes to search
		String queryAtributes = "lower(e.organizationUnit.name) like ?1";
		for (String atribute : arraySearchBy) {
			queryAtributes += " or lower(e." + atribute + ") like ?1";
		}

		return queryAtributes;
	}

	@Override
	public UserBO getSessionUser() {
		UserBO sessionUser;
		if (!identity.isLoggedIn() || identity.getAccount() == null) {
			sessionUser = null;
		} else {
			sessionUser = (UserBO) identity.getAccount();
		}
		return sessionUser;
	}

	@Override
	public List<PermissionBO> getUserPermissionsGrant(Long id) {
		List<PermissionBO> allPermissionsGranted = new ArrayList<>();
		Set<PermissionBO> permissionsSet = new LinkedHashSet<>();
		User user = userDao.findById(id);
		user.getOuPermissions().stream().forEach(ouPermission -> {
			Permission permission = permissionDAO.findById((Long) ouPermission.getPermission().getId());

			PermissionBO permissionBO = new PermissionBO(permission, true);
			List<PermissionBO> permissionsGranted = permissionBO.getPermissionsGrant();
			permissionsSet.addAll(permissionsGranted);
		});

		allPermissionsGranted.addAll(permissionsSet);
		return allPermissionsGranted;
	}

}
