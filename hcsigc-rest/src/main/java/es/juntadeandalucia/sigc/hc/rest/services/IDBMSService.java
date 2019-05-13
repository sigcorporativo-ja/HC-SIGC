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

import es.juntadeandalucia.sigc.hc.core.bo.DBMSBO;
import es.juntadeandalucia.sigc.hc.rest.dto.DBMSDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;

public interface IDBMSService {

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<DBMSDTO> getAllDBMS();

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DBMSDTO, DBMSBO> paginate(@QueryParam("start") int start, @QueryParam("size") int size);

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DBMSDTO getDBMSById(@PathParam("id") Long id);

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DBMSDTO, DBMSBO> search(@QueryParam("start") int start, @QueryParam("size") int size, @QueryParam("match") String match, @QueryParam("searchBy") String searchBy);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createDBMS(DBMSDTO dbmsDTO);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateDBMS(@PathParam("id") long id, DBMSDTO dbmsDTO);

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeDBMS(@PathParam("id") long id);

}
