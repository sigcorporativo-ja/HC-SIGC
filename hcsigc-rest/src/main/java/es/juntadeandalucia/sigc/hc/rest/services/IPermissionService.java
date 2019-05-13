package es.juntadeandalucia.sigc.hc.rest.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.juntadeandalucia.sigc.hc.rest.dto.PermissionDTO;

public interface IPermissionService {
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<PermissionDTO> getAllPermissions();
}
