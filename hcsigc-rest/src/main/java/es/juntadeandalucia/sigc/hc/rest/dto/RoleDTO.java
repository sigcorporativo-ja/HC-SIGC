package es.juntadeandalucia.sigc.hc.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.RoleBO;

public class RoleDTO extends GenericDTO<RoleBO> {

	@JsonProperty("name")
	private String name;

	@JsonProperty("permissions")
	private List<PermissionDTO> permissions;

	public RoleDTO() {
		super();
	}

	public RoleDTO(RoleBO roleBO) {
		super(roleBO);

		if (permissions == null) {
			permissions = new ArrayList<>();
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PermissionDTO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionDTO> permissions) {
		this.permissions = permissions;
	}

	@Override
	protected void initalizeFromBO(RoleBO bo) {
		this.id = bo.getId();
		this.name = bo.getName();
		if (bo.getPermissions() != null) {
			this.permissions = bo.getPermissions().stream().map(PermissionDTO::new).collect(Collectors.toList());
		}
	}

}
