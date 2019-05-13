package es.juntadeandalucia.sigc.hc.core.controller;

import java.io.Serializable;
import java.util.List;

import es.juntadeandalucia.sigc.hc.core.bo.GroupBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.persistence.entity.Group;
import es.juntadeandalucia.sigc.hc.persistence.entity.GroupUser;

public interface IGroupController extends Serializable {

	List<GroupBO> getAllGroup();

	/**
	 * Retrieves Group applying pagination
	 *
	 * @param start the index of the first group
	 * @param size  of the page
	 *
	 * @return a subset of Group
	 */
	public PaginatedBO<GroupBO> paginate(int start, int size);

	GroupBO findById(Long id);

	void createGroup(GroupBO groupBO);

	void updateGroup(GroupBO groupBO);

	void removeGroup(GroupBO groupBO);

	public PaginatedBO<GroupBO> findByField(String field, String match);
	
	PaginatedBO<GroupBO> search(int start, int size, String match, String searchBy);

	List<GroupUser> updateGroupUsers(GroupBO groupBO, Group group);

	List<GroupBO> getGroupsToShare();
}