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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.juntadeandalucia.sigc.hc.core.bo.OrganizationUnitBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.bo.UserBO;
import es.juntadeandalucia.sigc.hc.core.controller.IOrganizationUnitController;
import es.juntadeandalucia.sigc.hc.core.controller.impl.UserController;
import es.juntadeandalucia.sigc.hc.core.interceptor.binding.HasPermissions;
import es.juntadeandalucia.sigc.hc.rest.dto.OrganizationUnitDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;
import es.juntadeandalucia.sigc.hc.rest.services.IOrganizationUnitService;

@Path("/ou")
public class OrganizationUnitService implements IOrganizationUnitService {

	@Inject
	private IOrganizationUnitController organizationUnitController;
	
	@Inject 
	private UserController userController;

	@Override
	@GET
	@Path("/")
	@HasPermissions({"ADMIN_UO"})
	@Produces({ MediaType.APPLICATION_JSON })
	public List<OrganizationUnitDTO> getAllOrganizationUnit() {
		return organizationUnitController.getAllOrganizationUnit().stream().map(OrganizationUnitDTO::new)
				.collect(Collectors.toList());
	}

	@Override
	@GET
	@Path("/paginate")
	@HasPermissions({"ADMIN_UO"})
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<OrganizationUnitDTO, OrganizationUnitBO> paginate(@QueryParam("start") int start,
			@QueryParam("size") int size) {
		PaginatedBO<OrganizationUnitBO> paginatedBO = organizationUnitController.paginate(start, size);
		return new PaginatedDTO<>(paginatedBO, OrganizationUnitDTO.class, OrganizationUnitBO.class);
	}

	@Override
	@GET
	@Path("/{id}")
	@HasPermissions({"ADMIN_UO"})
	@Produces({ MediaType.APPLICATION_JSON })
	public OrganizationUnitDTO getOrganizationUnitById(@PathParam("id") Long id) {
		return new OrganizationUnitDTO(organizationUnitController.findById(id));
	}
	
	@Override
	@GET
	@Path("/myou")
	@HasPermissions({"ADMIN_GROUP_USERS"})
	@Produces({ MediaType.APPLICATION_JSON })
	public OrganizationUnitDTO getMyOrganizationUnit() {
		UserBO sessionUser = userController.getSessionUser();
		OrganizationUnitBO ouBO = organizationUnitController.findById(sessionUser.getOrganizationUnit().getId());
		return new OrganizationUnitDTO(ouBO);
	}
	
	@GET
	@Path("/search")
	@HasPermissions({"ADMIN_UO"})
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<OrganizationUnitDTO, OrganizationUnitBO> search(@QueryParam("start") int start, 
			@QueryParam("size") int size, @QueryParam("match") String match, @QueryParam("searchBy") String searchBy) {
		PaginatedBO<OrganizationUnitBO> paginatedBO = organizationUnitController.search(start, size, match, searchBy);
		return new PaginatedDTO<>(paginatedBO, OrganizationUnitDTO.class, OrganizationUnitBO.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@HasPermissions({"ADMIN_UO"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOrganizationUnit(OrganizationUnitDTO organizationUnitDTO) {
		OrganizationUnitBO organizationUnitBO = organizationUnitDTO.toBO();
		organizationUnitController.createOrganizationUnit(organizationUnitBO);
		return Response.ok().build();
	}

	@Override
	@DELETE
	@Path("/{id}")
	@HasPermissions({"ADMIN_UO"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeOrganizationUnit(@PathParam("id") long id) {
		Response result = null;
		OrganizationUnitBO organizationUnitBO = new OrganizationUnitBO();
		organizationUnitBO.setId(id);
		try {
			organizationUnitController.removeOrganizationUnit(organizationUnitBO);
			result = Response.ok().build();
		}
		catch ( RuntimeException e ) {
			result = Response.serverError().build();
		}
		return result;
	}

	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@HasPermissions({"ADMIN_UO"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateOrganizationUnit(@PathParam("id") long id, OrganizationUnitDTO organizationUnitDTO) {
		OrganizationUnitBO organizationUnitBO = organizationUnitDTO.toBO(id);
		organizationUnitController.updateOrganizationUnit(organizationUnitBO);
		return Response.ok().build();
	}
}
