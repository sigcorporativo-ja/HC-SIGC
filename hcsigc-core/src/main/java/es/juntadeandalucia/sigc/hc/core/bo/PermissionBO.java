package es.juntadeandalucia.sigc.hc.core.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.juntadeandalucia.sigc.hc.persistence.entity.Permission;

public class PermissionBO extends GenericBO<Permission> {

	private static final long serialVersionUID = -5582573445265288872L;

	private String code;

	private String name;

	private List<PermissionBO> permissionsGrant;

	private List<RoleBO> rolesBO;

	public PermissionBO() {
		super();
	}

	public PermissionBO(Permission perm) {
		this(perm, false);
	}

	public PermissionBO(Permission perm, boolean eager) {
		super(perm, eager);
		if (permissionsGrant == null) {
			permissionsGrant = new ArrayList<>();
		}

		if (rolesBO == null) {
			rolesBO = new ArrayList<>();
		}
	}

	@Override
	protected void initializeFromEntity(Permission entity, boolean eager) {
		this.setId((long) entity.getId());
		this.code = entity.getCode();
		this.name = entity.getName();
		if (eager) {
			this.permissionsGrant = entity.getPermissionsGranted().stream().map(PermissionBO::new)
					.collect(Collectors.toList());
			this.rolesBO = entity.getRoles().stream().map(RoleBO::new).collect(Collectors.toList());
		}
	}

	public List<PermissionBO> getPermissionsGrant() {
		return permissionsGrant;
	}

	public void setPermissionsGrant(List<PermissionBO> permissionsGrant) {
		this.permissionsGrant = permissionsGrant;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermissionBO other = (PermissionBO) obj;
		if (this.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.getId().equals(other.getId()))
			return false;
		return true;
	}

}
