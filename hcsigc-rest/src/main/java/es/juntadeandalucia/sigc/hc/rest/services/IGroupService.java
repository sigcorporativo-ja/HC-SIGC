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

import es.juntadeandalucia.sigc.hc.core.bo.GroupBO;
import es.juntadeandalucia.sigc.hc.rest.dto.GroupDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;

public interface IGroupService {

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<GroupDTO> getAllGroup();

	@GET
	@Path("/toshare")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<GroupDTO> getGroupsToShare();
	
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<GroupDTO, GroupBO> paginate(@QueryParam("start") int start, @QueryParam("size") int size);

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public GroupDTO getGroupById(@PathParam("id") Long id);

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<GroupDTO, GroupBO> search(@QueryParam("start") int start, @QueryParam("size") int size, @QueryParam("match") String match, @QueryParam("searchBy") String searchBy);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createGroup(GroupDTO groupDTO);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateGroup(@PathParam("id") long id, GroupDTO groupDTO);

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeGroup(@PathParam("id") long id);
}
