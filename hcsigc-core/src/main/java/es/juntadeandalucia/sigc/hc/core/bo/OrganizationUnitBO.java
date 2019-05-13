package es.juntadeandalucia.sigc.hc.core.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.juntadeandalucia.sigc.hc.persistence.entity.OrganizationUnit;

public class OrganizationUnitBO extends GenericBO<OrganizationUnit> {

	private static final long serialVersionUID = 5537610682731927896L;

	private String name;

	private String description;

	private List<GroupBO> groups = new ArrayList<>();

	private List<UserBO> users = new ArrayList<>();

	public OrganizationUnitBO() {
		super();
	}

	public OrganizationUnitBO(OrganizationUnit uo) {
		this(uo, false);
	}

	public OrganizationUnitBO(OrganizationUnit uo, boolean eager) {
		super(uo, eager);
	}

	@Override
	protected void initializeFromEntity(OrganizationUnit entity, boolean eager) {
		if (entity != null) {
			this.setId((Long) entity.getId());
			this.name = entity.getName();
			this.description = entity.getDescription();
			if (eager) {
				this.users = entity.getUsers().stream().map(UserBO::new).collect(Collectors.toList());
				this.groups = entity.getGroups().stream().map(GroupBO::new).collect(Collectors.toList());
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

	public List<GroupBO> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupBO> groups) {
		this.groups = groups;
	}

	public List<UserBO> getUsers() {
		return users;
	}

	public void setUsers(List<UserBO> users) {
		this.users = users;
	}

	public OrganizationUnit toEntity() {
		OrganizationUnit ou = new OrganizationUnit();
		ou.setId(this.getId());
		ou.setName(this.getName());
		ou.setDescription(this.getDescription());
		return ou;
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
				OrganizationUnitBO other = (OrganizationUnitBO) obj;
				if (getId() != other.getId()) {
					result = false;
				}
			}
		}
		return result;

	}

}
