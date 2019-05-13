package es.juntadeandalucia.sigc.hc.core.bo;

import java.util.List;
import java.util.stream.Collectors;

import es.juntadeandalucia.sigc.hc.persistence.entity.Role;

public class RoleBO extends GenericBO<Role> {

	private static final long serialVersionUID = -8382787491198047085L;

	private String name;

	private List<PermissionBO> permissions;

	public RoleBO() {
		super();
	}
	
	public RoleBO(Role role) {
		this(role, false);
	}
	
	public RoleBO(Role role, boolean eager) {
		super(role, eager);
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PermissionBO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionBO> permissions) {
		this.permissions = permissions;
	}

	@Override
	protected void initializeFromEntity(Role entity, boolean eager) {
		if (entity != null) {
			this.setId((Long) entity.getId());
			this.setName(entity.getName());
			if (eager) {
				this.permissions = entity.getPermissions().stream().map(PermissionBO::new).collect(Collectors.toList());
			}
		}

	}
}
