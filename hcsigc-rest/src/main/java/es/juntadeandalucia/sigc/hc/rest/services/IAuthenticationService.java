package es.juntadeandalucia.sigc.hc.rest.services;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.juntadeandalucia.sigc.hc.rest.dto.CredentialsDTO;

public interface IAuthenticationService {

	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@Context HttpHeaders httpHeaders, CredentialsDTO credentials);

	@POST
	@Path("logout")
	public Response logout(@Context HttpHeaders httpHeaders);

	@POST
	@Path("refresh")
	public Response refresh(@Context HttpHeaders httpHeaders);

}
