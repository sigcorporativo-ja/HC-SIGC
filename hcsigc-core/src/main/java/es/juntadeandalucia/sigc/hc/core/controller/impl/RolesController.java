package es.juntadeandalucia.sigc.hc.core.controller.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.guadaltel.framework.core.User;
import es.juntadeandalucia.sigc.hc.core.bo.RoleBO;
import es.juntadeandalucia.sigc.hc.core.controller.IRolesController;
import es.juntadeandalucia.sigc.hc.persistence.dao.IRoleDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.Role;

@Stateless
@Transactional
public class RolesController implements IRolesController, Serializable {

	private static final Log LOG = LogFactory.getLog(RolesController.class);

	private static final long serialVersionUID = -7627995359268110090L;

	@Inject
	IRoleDAO roleDAO;

	@Override
	public List<RoleBO> getRolesByUser(User user) {
		List<RoleBO> roles = new LinkedList<>();

		RoleBO admin = new RoleBO();
		admin.setName("ADMIN");
		roles.add(admin);

		LOG.info("List of Rols by User");
		return roles;
	}

	public List<RoleBO> getAllRoles() {
		return roleDAO.findAllEager().stream().map(role -> new RoleBO(role, true)).collect(Collectors.toList());
	}

	public RoleBO getRoleById(Integer id) {
		Role role = roleDAO.findById(id);
		return new RoleBO(role, true);
	}

}
