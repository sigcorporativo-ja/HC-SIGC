package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import es.juntadeandalucia.sigc.hc.persistence.dao.IGroupDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.Group;
import es.juntadeandalucia.sigc.hc.persistence.entity.GroupUser;

@Stateless
public class GroupDAO extends GenericDAO<Group, Long> implements IGroupDAO {
	
	private static final Log LOG = LogFactory.getLog(GroupDAO.class);
	
	private static final long serialVersionUID = 8917590084482130287L;

	@Override
	public Group findById(Long id) {
		Group group;

		try {
			group = getEntityManager().createQuery("select g from Group g where g.id = ?", Group.class)
					.setParameter(1, id).getSingleResult();
			Hibernate.initialize(group.getOrganizationUnit());
			Hibernate.initialize(group.getDbconnections());
			Hibernate.initialize(group.getGroupsUsers());
			LOG.info("Object group whith id=" + id + " found");
		} catch (NoResultException e) {
			LOG.error("No group found by that credentials", e);
			group = null;
		}

		return group;
	}

	@Override
	public void createGroupUser(GroupUser groupUser) {
		getEntityManager().persist(groupUser);
	}
	
	@Override
	public List<GroupUser> findGroupUsersByGroupId(Long id){
		List<GroupUser> result = new ArrayList<>();
		
		try {
			result = getEntityManager()
					.createQuery("select g from GroupUser g where g.group = " + id, GroupUser.class)
					.getResultList();
			LOG.info("GroupUsers by group id=" + id + "found");
		} catch (NoResultException e) {
			LOG.error("GroupUsers by group id=" + id + "have not been found" , e);
			result = null;
		}
		
		return result;
	}
	
	@Override
	public void removeGroupUser(GroupUser entity) {
		entity = getEntityManager().getReference(GroupUser.class, entity.getId());
		if (entity != null) {
			getEntityManager().remove(entity);
		}
	}

	@Override
	public List<Group> findToShare(Long userId, boolean isAdminGroup, boolean isSuperAdmin) {
		List<Group> result = new ArrayList<>();
		String query = "select distinct gu.group from GroupUser gu where gu.user.id = " + userId;
		
		if(isAdminGroup)
			query = query + 
				" and gu.group.organizationUnit = (select u.organizationUnit from User u where u.id = " + userId +")";
		if(isSuperAdmin)
			query = "select distinct gu.group from GroupUser gu";
			
		try {
			result = getEntityManager()
					.createQuery(query, Group.class)
					.getResultList();
			LOG.info("Groups to share by user id=" + userId + "found");
		} catch (NoResultException e) {
			LOG.error("Groups to share by user id=" + userId + "have not been found" , e);
			result = null;
		}
		
		return result;
	}
	
}
