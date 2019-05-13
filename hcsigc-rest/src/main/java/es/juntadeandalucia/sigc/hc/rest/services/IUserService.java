package es.juntadeandalucia.sigc.hc.rest.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.juntadeandalucia.sigc.hc.rest.dto.PermissionDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.UserCreationDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.UserDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.UserPaginatedDTO;

public interface IUserService {

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<UserDTO> getAllUser();

	@GET
	@Path("/session")
	@Produces({ MediaType.APPLICATION_JSON })
	public UserDTO getUserSession(@Context HttpHeaders httpHeaders);

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public UserPaginatedDTO paginate(@QueryParam("start") int start, @QueryParam("size") int size);

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public UserDTO getUserById(@PathParam("id") Long id);

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON })
	public UserPaginatedDTO search(@QueryParam("start") int start, @QueryParam("size") int size,
			@QueryParam("match") String match, @QueryParam("searchBy") String searchBy);

	@GET
	@Path("/permissions_grant/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<PermissionDTO> getUserPermissionsGrant(@PathParam("id") Long id);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createUser(UserCreationDTO userDTO);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateUser(@PathParam("id") long id, UserCreationDTO userCreationDTO);

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeUser(@PathParam("id") long id);

}
