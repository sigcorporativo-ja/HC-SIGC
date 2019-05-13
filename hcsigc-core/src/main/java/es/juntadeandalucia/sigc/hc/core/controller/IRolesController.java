package es.juntadeandalucia.sigc.hc.core.controller;

import java.io.Serializable;
import java.util.List;

import es.guadaltel.framework.core.User;
import es.juntadeandalucia.sigc.hc.core.bo.RoleBO;

public interface IRolesController extends Serializable {

	List<RoleBO> getRolesByUser(User user);

	List<RoleBO> getAllRoles();
	
	RoleBO getRoleById(Integer id);
}
