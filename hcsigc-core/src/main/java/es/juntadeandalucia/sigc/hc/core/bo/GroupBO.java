package es.juntadeandalucia.sigc.hc.core.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.juntadeandalucia.sigc.hc.persistence.entity.Group;

public class GroupBO extends GenericBO<Group> {

	private static final long serialVersionUID = 4923835858371451313L;

	private String name;

	private String description;

	private OrganizationUnitBO organizationUnit;

	private List<DBConnectionBO> dbconnections;

	private List<UserBO> users;

	public GroupBO() {
		super();
	}

	public GroupBO(Group group) {
		this(group, false);
		if(this.dbconnections == null) {
			dbconnections = new ArrayList<>();
		}
		if(this.users == null) {
			users = new ArrayList<>();
		}
	}

	public GroupBO(Group group, boolean eager) {
		super(group, eager);
	}

	@Override
	protected void initializeFromEntity(Group group, boolean eager) {
		if (group != null) {
			this.setId((Long) group.getId());
			this.name = group.getName();
			this.description = group.getDescription();
			this.organizationUnit = new OrganizationUnitBO(group.getOrganizationUnit());
			if (eager) {
				this.dbconnections = group.getDbconnections().stream().map(DBConnectionBO::new)
						.collect(Collectors.toList());
				this.users = group.getGroupsUsers().stream().map(g -> new UserBO(g.getUser()))
						.collect(Collectors.toList());
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public OrganizationUnitBO getOrganization() {
		return organizationUnit;
	}

	public void setOrganization(OrganizationUnitBO organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	public List<DBConnectionBO> getDbconnections() {
		return dbconnections;
	}

	public void setDbconnections(List<DBConnectionBO> dbconnections) {
		this.dbconnections = dbconnections;
	}

	public List<UserBO> getUsers() {
		return users;
	}

	public void setUsers(List<UserBO> users) {
		this.users = users;
	}

	public Group toEntity() {
		Group group = new Group();
		group.setId(this.getId());
		group.setName(this.getName());
		group.setDescription(this.getDescription());
		return group;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (getId() ^ (getId() >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		boolean result = true;
		if (this != obj) {
			if (obj == null || getClass() != obj.getClass()) {
				result = false;
			} else {
				GroupBO other = (GroupBO) obj;
				if (getId() != other.getId()) {
					result = false;
				}
			}
		}
		return result;

	}
}