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

import es.juntadeandalucia.sigc.hc.core.bo.DBConnectionBO;
import es.juntadeandalucia.sigc.hc.rest.dto.DBConnectionCreationDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.DBConnectionDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;

public interface IDBConnectionService {

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<DBConnectionDTO> getAllDBConnections();

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DBConnectionDTO, DBConnectionBO> paginate(@QueryParam("start") int start,
			@QueryParam("size") int size);

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DBConnectionDTO getDBConnectionById(@PathParam("id") Long id);

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DBConnectionDTO, DBConnectionBO> search(@QueryParam("start") int start, @QueryParam("size") int size, @QueryParam("match") String match, @QueryParam("searchBy") String searchBy);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createDBConnection(DBConnectionCreationDTO dbConnectionCreationDTO);
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateDBConnection(@PathParam("id") long id, DBConnectionCreationDTO dbConnectionCreationDTO);

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeDBConnection(@PathParam("id") long id);

}
