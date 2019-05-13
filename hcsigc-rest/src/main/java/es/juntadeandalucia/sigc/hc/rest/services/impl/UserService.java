package es.juntadeandalucia.sigc.hc.rest.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
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

import es.juntadeandalucia.sigc.hc.core.bo.OrganizationUnitBO;
import es.juntadeandalucia.sigc.hc.core.bo.UserBO;
import es.juntadeandalucia.sigc.hc.core.bo.UserPaginatedBO;
import es.juntadeandalucia.sigc.hc.core.controller.IUserController;
import es.juntadeandalucia.sigc.hc.core.interceptor.binding.HasPermissions;
import es.juntadeandalucia.sigc.hc.rest.auth.Authenticator;
import es.juntadeandalucia.sigc.hc.rest.dto.PermissionDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.UserCreationDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.UserDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.UserPaginatedDTO;
import es.juntadeandalucia.sigc.hc.rest.services.IUserService;

@Path("/users")
public class UserService implements IUserService {

	@Inject
	private IUserController userController;

	@Inject
	private Authenticator authenticator;

	@Override
	@GET
	@Path("/")
	@HasPermissions({ "ADMIN_GROUP_USERS" })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<UserDTO> getAllUser() {
		return userController.getAllUser().stream().map(UserDTO::new).collect(Collectors.toList());
	}

	@GET
	@Path("/session")
	@HasPermissions({ "ADMIN_USERS" })
	@Produces({ MediaType.APPLICATION_JSON })
	public UserDTO getUserSession(@Context HttpHeaders httpHeaders) {
		UserDTO user = null;

		UserBO userBO = authenticator.getUser(httpHeaders.getRequestHeaders());
		if (userBO != null) {
			user = new UserDTO(userBO);
		}

		return user;
	}

	@Override
	@GET
	@Path("/{id}")
	@HasPermissions({ "ADMIN_USERS" })
	@Produces({ MediaType.APPLICATION_JSON })
	public UserDTO getUserById(@PathParam("id") Long id) {
		return new UserDTO(userController.findById(id));
	}

	@GET
	@Path("/logged")
	@HasPermissions({ "VIEW_DATA" })
	@Produces({ MediaType.APPLICATION_JSON })
	public UserDTO userLogged() {
		UserDTO userLogged = new UserDTO();
		if (userController.getSessionUser() != null)
			userLogged = new UserDTO(userController.getSessionUser());
		UserDTO userReturn = new UserDTO();
		userReturn.setId(userLogged.getId());
		userReturn.setName(userLogged.getName());
		userReturn.setMail(userLogged.getMail());

		return userReturn;
	}

	@Override
	@GET
	@Path("/paginate")
	@HasPermissions({ "ADMIN_USERS" })
	@Produces({ MediaType.APPLICATION_JSON })
	public UserPaginatedDTO paginate(@QueryParam("start") int start, @QueryParam("size") int size) {
		UserPaginatedBO paginatedBO = userController.paginate(start, size);
		return new UserPaginatedDTO(paginatedBO);
	}

	@GET
	@Path("/search")
	@HasPermissions({ "ADMIN_USERS" })
	@Produces({ MediaType.APPLICATION_JSON })
	public UserPaginatedDTO search(@QueryParam("start") int start, @QueryParam("size") int size,
			@QueryParam("match") String match, @QueryParam("searchBy") String searchBy) {
		UserPaginatedBO paginatedBO = userController.search(start, size, match, searchBy);
		return new UserPaginatedDTO(paginatedBO);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@HasPermissions({ "ADMIN_USERS" })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createUser(UserCreationDTO userCreationDTO) {
		OrganizationUnitBO ouBO = userCreationDTO.getOrganizationUnit()
				.toBO(userCreationDTO.getOrganizationUnit().getId());
		UserBO userBO = userCreationDTO.toBO();
		userBO.setOrganizationUnit(ouBO);
		userController.createUser(userBO);
		return Response.ok().build();
	}

	@Override
	@DELETE
	@Path("/{id}")
	@HasPermissions({ "ADMIN_USERS" })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeUser(@PathParam("id") long id) {
		UserBO userBO = userController.findById(id);
		userController.removeUser(userBO);
		return Response.ok().build();
	}

	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@HasPermissions({ "ADMIN_USERS" })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateUser(@PathParam("id") long id, UserCreationDTO usercreationDTO) {
		OrganizationUnitBO ouBO = usercreationDTO.getOrganizationUnit()
				.toBO(usercreationDTO.getOrganizationUnit().getId());
		UserBO userBO = usercreationDTO.toBO(id);
		userBO.setOrganizationUnit(ouBO);
		userController.updateUser(userBO);
		return Response.ok().build();
	}

	@Override
	public List<PermissionDTO> getUserPermissionsGrant(Long id) {
		return userController.getUserPermissionsGrant(id).stream().map(PermissionDTO::new).collect(Collectors.toList());

	}

}
