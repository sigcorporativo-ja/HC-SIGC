package es.juntadeandalucia.sigc.hc.rest.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.juntadeandalucia.sigc.hc.core.bo.RoleBO;
import es.juntadeandalucia.sigc.hc.core.controller.IRolesController;
import es.juntadeandalucia.sigc.hc.rest.services.IRoleService;
import es.juntadeandalucia.sigc.hc.rest.dto.RoleDTO;

@Path("/roles")
public class RoleService implements IRoleService {

	@Inject
	private IRolesController rolesController;

	@Override
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<RoleDTO> getAllRoles() {
		return rolesController.getAllRoles().stream().map(RoleDTO::new).collect(Collectors.toList());

	}

	@Override
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public RoleDTO getRoleById(@PathParam("id") Integer id) {
		RoleBO roleBO = rolesController.getRoleById(id);
		return new RoleDTO(roleBO);
	}

}
