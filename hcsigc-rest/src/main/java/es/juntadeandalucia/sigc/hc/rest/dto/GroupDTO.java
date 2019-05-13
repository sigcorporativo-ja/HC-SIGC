package es.juntadeandalucia.sigc.hc.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.GroupBO;

public class GroupDTO extends GenericDTO<GroupBO> {

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("users")
	private List<UserDTO> users;

	@JsonProperty("org_unit")
	private OrganizationUnitDTO ou;

	@JsonProperty("dbconnections")
	private List<DBConnectionDTO> dbconnections;

	public GroupDTO() {
		super();
	}

	public GroupDTO(GroupBO groupBO) {
		super(groupBO);
		if(users == null) {
			users = new ArrayList<>();
		}
		
		if(dbconnections == null) {
			dbconnections = new ArrayList<>();
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

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public OrganizationUnitDTO getOu() {
		return ou;
	}

	public void setOu(OrganizationUnitDTO ou) {
		this.ou = ou;
	}

	public List<DBConnectionDTO> getDbconnections() {
		return dbconnections;
	}

	public void setDbconnections(List<DBConnectionDTO> dbconnections) {
		this.dbconnections = dbconnections;
	}

	public GroupBO toBO(long id) {
		GroupBO groupBO = new GroupBO();
		groupBO.setId(id);
		groupBO.setName(this.getName());
		groupBO.setDescription(this.getDescription());
		return groupBO;
	}

	public GroupBO toBO() {
		GroupBO groupBO = new GroupBO();
		groupBO.setName(this.getName());
		groupBO.setDescription(this.getDescription());
		return groupBO;
	}

	@Override
	protected void initalizeFromBO(GroupBO groupBO) {
		this.id = groupBO.getId();
		this.name = groupBO.getName();
		this.description = groupBO.getDescription();
		if (groupBO.getOrganization() != null) {
			this.ou = new OrganizationUnitDTO(groupBO.getOrganization());
		}
		if (groupBO.getUsers() != null) {
			this.users = groupBO.getUsers().stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
		}
		if (groupBO.getDbconnections() != null) {
			this.dbconnections = groupBO.getDbconnections().stream()
					.map(dbconnection -> new DBConnectionDTO(dbconnection)).collect(Collectors.toList());
		}

	}
}