package es.juntadeandalucia.sigc.hc.persistence.dao;

import java.util.List;

import es.juntadeandalucia.sigc.hc.persistence.entity.Group;
import es.juntadeandalucia.sigc.hc.persistence.entity.GroupUser;

public interface IGroupDAO extends IGenericDAO<Group, Long> {

	void createGroupUser(GroupUser gu);
	
	void removeGroupUser(GroupUser gu);
	
	List<GroupUser> findGroupUsersByGroupId(Long id);

	List<Group> findToShare(Long userId, boolean isAdminGroup, boolean isSuperAdmin);
}
