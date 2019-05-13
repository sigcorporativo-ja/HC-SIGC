package es.juntadeandalucia.sigc.hc.core.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.juntadeandalucia.sigc.hc.core.bo.DBConnectionBO;
import es.juntadeandalucia.sigc.hc.core.bo.GroupBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.bo.UserBO;
import es.juntadeandalucia.sigc.hc.core.controller.IDBConnectionController;
import es.juntadeandalucia.sigc.hc.core.controller.IGroupController;
import es.juntadeandalucia.sigc.hc.core.controller.IUserController;
import es.juntadeandalucia.sigc.hc.persistence.dao.IGroupDAO;
import es.juntadeandalucia.sigc.hc.persistence.dao.IOrganizationUnitDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.DBConnection;
import es.juntadeandalucia.sigc.hc.persistence.entity.Group;
import es.juntadeandalucia.sigc.hc.persistence.entity.GroupUser;
import es.juntadeandalucia.sigc.hc.persistence.entity.OrganizationUnit;
import es.juntadeandalucia.sigc.hc.persistence.entity.User;

@Transactional
public class GroupController implements IGroupController {

	private static final Log LOG = LogFactory.getLog(GroupController.class);

	private static final long serialVersionUID = 2403515962491198524L;

	@Inject
	private IGroupDAO groupDao;

	@Inject
	private IOrganizationUnitDAO ouDao;

	@Inject
	private IUserController userController;

	@Inject
	private IDBConnectionController dbconnectionController;

	@Override
	public List<GroupBO> getAllGroup() {
		List<GroupBO> result = groupDao.findAll().stream().map(GroupBO::new).collect(Collectors.toList());
		LOG.info("List of Groups");
		return result;
	}
	
	@Override
	public List<GroupBO> getGroupsToShare() {
		String[] uoPermissions = {"ADMIN_GROUP"};
		String[] uoPermissions2 = {"ADMIN_UO"};
		UserBO sessionUser = userController.getSessionUser();
		boolean isAdminGroup = false;
		boolean isSuperAdmin = false;
		
		Long userId = Long.valueOf( sessionUser.getId() );
		if (userController.hasPermissions(sessionUser, uoPermissions)) {
			isAdminGroup = true;
		}
		if (userController.hasPermissions(sessionUser, uoPermissions2)) {
			isSuperAdmin = true;
		}
		
		List<GroupBO> result = groupDao.findToShare(userId, isAdminGroup, isSuperAdmin).stream().map(GroupBO::new).collect(Collectors.toList());
		LOG.info("List of Groups to share");
		return result;
	}

	@Override
	public PaginatedBO<GroupBO> paginate(int start, int size) {
		String[] uoPermissions = {"ADMIN_UO"};
		UserBO sessionUser = userController.getSessionUser();
		
		List<GroupBO> data = new ArrayList<>();
		Long count = null;
		if (!userController.hasPermissions(sessionUser, uoPermissions)) {
			data = groupDao.paginateByOrganizationUnit(start, size, sessionUser.getOrganizationUnit().getName()).stream().map(GroupBO::new).collect(Collectors.toList());
			count = groupDao.countByOrganizationUnit(sessionUser.getOrganizationUnit().getName());
		}else {
			data = groupDao.paginate(start, size).stream().map(GroupBO::new).collect(Collectors.toList());
			count = groupDao.count();
		}
		
		PaginatedBO<GroupBO> result = new PaginatedBO<>(count, data);
		LOG.info("Paginate Groups");
		return result;
	}

	public PaginatedBO<GroupBO> findByField(String field, String match) {
		List<GroupBO> data = groupDao.findByField(field, match).stream().map(GroupBO::new).collect(Collectors.toList());
		Long count = (long) data.size();
		PaginatedBO<GroupBO> result = new PaginatedBO<>(count, data);
		LOG.info("Paginate with field Group");
		return result;
	}

	@Override
	public void updateGroup(GroupBO groupBO) {
		String[] uoPermissions = {"ADMIN_UO"};
		UserBO sessionUser = userController.getSessionUser();
		
		if (userController.hasPermissions(sessionUser, uoPermissions) 
				|| groupBO.getOrganization().getId().equals(sessionUser.getOrganizationUnit().getId())) {
			Group group = groupDao.findById(groupBO.getId());
			group.setName(groupBO.getName());
			group.setDescription(groupBO.getDescription());
			OrganizationUnit ou = ouDao.findById(groupBO.getOrganization().getId());
			group.setOrganizationUnit(ou);
			
			List<DBConnection> dbconnections = new ArrayList<>();
			groupBO.getDbconnections().stream().forEach(udb -> {
				DBConnectionBO dbBO = dbconnectionController.findById(udb.getId());
				DBConnection db = dbBO.getDbConnection();
				dbconnections.add(db);
			});
			group.setDbconnections(dbconnections);
			
			groupDao.save(group);
			LOG.info("Object Group updated");
			
			//Get the relations GroupUsers and uptdate them
			List<GroupUser> newGroupUsers = updateGroupUsers(groupBO, group);
			group.setGroupsUsers(newGroupUsers);
		}else {
			LOG.info("Update object Group denegated");
		}
	}
	
	@Override
	public List<GroupUser> updateGroupUsers(GroupBO groupBO, Group group) {
		List<GroupUser> result = new ArrayList<>();
		List<GroupUser> groupUsers = groupDao.findById(groupBO.getId()).getGroupsUsers();
		List<User> oldUsers = groupUsers.stream().map(user -> user.getUser()).collect(Collectors.toList());
		
		//Create the new GroupUsers
		groupBO.getUsers().stream().forEach(ubo -> {
			User user = userController.findById(Long.parseLong(ubo.getId())).toEntity();
			if(!oldUsers.contains(user)) {
				GroupUser gu = new GroupUser();
				gu.setGroup(group);
				gu.setUser(user);
				groupDao.createGroupUser(gu);
				LOG.info("Object GroupUser created");
				result.add(gu);
			}
		});
		
		//Delete the removed GroupUsers
		groupUsers.stream().forEach(gu -> {
			List<String> lis = groupBO.getUsers().stream().map(x -> x.getId()).collect(Collectors.toList());
			if(!lis.contains( gu.getUser().getId().toString() )) { //If the user no longer exists in the group
				groupDao.removeGroupUser(gu);
				LOG.info("Object GroupUser removed");
			}else { //If it was already added/created
				result.add(gu);
			}
		});
		
		return result;
	}

	@Override
	public void createGroup(GroupBO groupBO) {
		String[] uoPermissions = {"ADMIN_UO"};
		UserBO sessionUser = userController.getSessionUser();
		
		if (userController.hasPermissions(sessionUser, uoPermissions) 
				|| groupBO.getOrganization().getId().equals(sessionUser.getOrganizationUnit().getId())) {
			Group group = groupBO.toEntity();

			OrganizationUnit ou = ouDao.findById(groupBO.getOrganization().getId());
			group.setOrganizationUnit(ou);

			List<DBConnection> dbconnections = new ArrayList<>();
			groupBO.getDbconnections().stream().forEach(udb -> {
				DBConnectionBO dbBO = dbconnectionController.findById(udb.getId());
				DBConnection db = dbBO.getDbConnection();
				dbconnections.add(db);
			});
			group.setDbconnections(dbconnections);
			groupDao.create(group);
			LOG.info("Object Group created");

			// creates the relation between users
			groupBO.getUsers().stream().forEach(ubo -> {
				User user = userController.findById(Long.parseLong(ubo.getId())).toEntity();
				GroupUser gu = new GroupUser();
				gu.setGroup(group);
				gu.setUser(user);
				groupDao.createGroupUser(gu);
			});
			LOG.info("Object GroupUser created");
		}else {
			LOG.info("Create object Group denegated");
		}
	}

	@Override
	public void removeGroup(GroupBO groupBO) {
		String[] uoPermissions = {"ADMIN_UO"};
		UserBO sessionUser = userController.getSessionUser();
		
		if (userController.hasPermissions(sessionUser, uoPermissions) 
				|| groupBO.getOrganization().getId().equals(sessionUser.getOrganizationUnit().getId())) {
			Group group = groupBO.toEntity();
			
			//Delete the GroupUsers
			groupDao.findById(groupBO.getId()).getGroupsUsers().stream().forEach(gu -> {
				groupDao.removeGroupUser(gu);
				LOG.info("Object GroupUser removed");
			});
			
			groupDao.remove(group);
			LOG.info("Object Group deleted");
		}else {
			LOG.info("Remove object Group denegated");
		}
	}

	@Override
	public GroupBO findById(Long id) {
		return new GroupBO(groupDao.findById(id), true);
	}
	
	@Override
	public PaginatedBO<GroupBO> search(int start, int size, String match, String searchBy) {
		String queryAtributes = prepareSearch(searchBy);
		
		String[] uoPermissions = {"ADMIN_UO"};
		UserBO sessionUser = userController.getSessionUser();
		
		List<GroupBO> data = new ArrayList<>();
		Long count = null;
		if (!userController.hasPermissions(sessionUser, uoPermissions)) {
			data = groupDao.searchByOrganizationUnit(start, size, match.toLowerCase(), queryAtributes, sessionUser.getOrganizationUnit().getName()).stream().map(GroupBO::new).collect(Collectors.toList());
			count = (long) groupDao.countSearchByOrganizationUnit(match.toLowerCase(), queryAtributes, sessionUser.getOrganizationUnit().getName());
		}else {
			data = groupDao.search(start, size, match.toLowerCase(), queryAtributes).stream().map(GroupBO::new).collect(Collectors.toList());
			count = (long) groupDao.countSearch(match.toLowerCase(), queryAtributes);
		}
		
		PaginatedBO<GroupBO> result = new PaginatedBO<>(count, data);
		LOG.info("Object PaginatedBO<GroupBO> using search found");
		return result;
	}
	
	private String prepareSearch(String searchBy){
		String[] arraySearchBy= searchBy.split(",");
		
		//Create the query with the attributes to search
		String queryAtributes = "lower(e.organizationUnit.name) like ?1";
		for(String atribute: arraySearchBy) {
			queryAtributes += " or lower(e." + atribute + ") like ?1";
		}
		
		return queryAtributes;
	}

}
