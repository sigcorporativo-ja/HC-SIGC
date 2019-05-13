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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.juntadeandalucia.sigc.hc.core.bo.OrganizationUnitBO;
import es.juntadeandalucia.sigc.hc.rest.dto.OrganizationUnitDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;

public interface IOrganizationUnitService {

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<OrganizationUnitDTO> getAllOrganizationUnit();
	
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<OrganizationUnitDTO, OrganizationUnitBO> paginate(@QueryParam("start") int start, @QueryParam("size") int size);

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public OrganizationUnitDTO getOrganizationUnitById(@PathParam("id") Long id);
	
	@GET
	@Path("/myou")
	@Produces({ MediaType.APPLICATION_JSON })
	public OrganizationUnitDTO getMyOrganizationUnit();
	
	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<OrganizationUnitDTO, OrganizationUnitBO> search(@QueryParam("start") int start, @QueryParam("size") int size, @QueryParam("match") String match, @QueryParam("searchBy") String searchBy);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOrganizationUnit(OrganizationUnitDTO organizationUnitDTO);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateOrganizationUnit(@PathParam("id") long id, OrganizationUnitDTO organizationUnitDTO);

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeOrganizationUnit(@PathParam("id") long id);

}
