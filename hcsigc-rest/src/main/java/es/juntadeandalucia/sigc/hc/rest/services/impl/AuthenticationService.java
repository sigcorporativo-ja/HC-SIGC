package es.juntadeandalucia.sigc.hc.rest.services.impl;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.LoginException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.juntadeandalucia.sigc.hc.rest.auth.Authenticator;
import es.juntadeandalucia.sigc.hc.rest.dto.AuthTokenDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.CredentialsDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.ErrorDTO;
import es.juntadeandalucia.sigc.hc.rest.services.IAuthenticationService;

@Path("/auth")
public class AuthenticationService implements IAuthenticationService {

	@Inject
	private Authenticator authenticator;

	@Override
	@PermitAll
	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@Context HttpHeaders httpHeaders, CredentialsDTO credentials) {
		Response response;
		try {
			String username = credentials.getUsername();
			String password = credentials.getPassword();

			AuthTokenDTO loginResponse = authenticator.login(username, password);
			response = getNoCacheResponseBuilder(Response.Status.OK).entity(loginResponse).build();
		} catch (final LoginException e) {
			response = getNoCacheResponseBuilder(Response.Status.UNAUTHORIZED)
					.entity(new ErrorDTO(Response.Status.UNAUTHORIZED, e.getLocalizedMessage()))
					.type(MediaType.APPLICATION_JSON).build();
		}
		return response;
	}

	@Override
	@PermitAll
	@POST
	@Path("logout")
	public Response logout(@Context HttpHeaders httpHeaders) {
		String authToken = authenticator.extractToken(httpHeaders.getRequestHeaders());

		authenticator.logout(authToken);

		return getNoCacheResponseBuilder(Response.Status.OK).entity(Json.createObjectBuilder().build().toString())
				.build();
	}

	@Override
	@PermitAll
	@POST
	@Path("refresh")
	public Response refresh(@Context HttpHeaders httpHeaders) {
		try {
			String authToken = authenticator.extractToken(httpHeaders.getRequestHeaders());

			AuthTokenDTO tokenResponse = authenticator.refresh(authToken);

			return getNoCacheResponseBuilder(Response.Status.OK).entity(tokenResponse).build();
		} catch (AccountExpiredException e) {
			JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
			jsonObjBuilder.add("error", "invalid_grant");
			JsonObject jsonObj = jsonObjBuilder.build();

			return getNoCacheResponseBuilder(Response.Status.BAD_REQUEST).entity(jsonObj.toString()).build();
		}
	}

	private Response.ResponseBuilder getNoCacheResponseBuilder(Response.Status status) {
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);
		cc.setMaxAge(-1);
		cc.setMustRevalidate(true);

		return Response.status(status).cacheControl(cc);
	}
}
