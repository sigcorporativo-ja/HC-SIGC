package es.juntadeandalucia.sigc.hc.rest.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.juntadeandalucia.sigc.hc.core.controller.IPermissionController;
import es.juntadeandalucia.sigc.hc.rest.services.IPermissionService;
import es.juntadeandalucia.sigc.hc.rest.dto.PermissionDTO;

@Path("/permissions")
public class PermissionService implements IPermissionService {

	@Inject
	private IPermissionController permissionController;

	@Override
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<PermissionDTO> getAllPermissions() {
		return permissionController.getAllPermissions().stream().map(PermissionDTO::new).collect(Collectors.toList());
	}

}
