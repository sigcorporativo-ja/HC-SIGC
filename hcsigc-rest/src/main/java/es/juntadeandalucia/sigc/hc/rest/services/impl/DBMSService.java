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

import es.juntadeandalucia.sigc.hc.core.bo.DBMSBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.controller.IDBMSController;
import es.juntadeandalucia.sigc.hc.core.interceptor.binding.HasPermissions;
import es.juntadeandalucia.sigc.hc.rest.dto.DBMSDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;
import es.juntadeandalucia.sigc.hc.rest.services.IDBMSService;

@Path("/dbms")
public class DBMSService implements IDBMSService {

	@Inject
	private IDBMSController dbmsController;

	@Override
	@GET
	@Path("/")
	@HasPermissions({"ADMIN_DATABASES"})
	@Produces({ MediaType.APPLICATION_JSON })
	public List<DBMSDTO> getAllDBMS() {
		return dbmsController.getAllDBMS().stream().map(DBMSDTO::new).collect(Collectors.toList());
	}

	@Override
	@GET
	@Path("/paginate")
	@HasPermissions({"ADMIN_DATABASES"})
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DBMSDTO, DBMSBO> paginate(@QueryParam("start") int start, @QueryParam("size") int size) {
		PaginatedBO<DBMSBO> paginatedBO = dbmsController.paginate(start, size);
		return new PaginatedDTO<>(paginatedBO, DBMSDTO.class, DBMSBO.class);
	}

	@Override
	@GET
	@Path("/{id}")
	@HasPermissions({"ADMIN_DATABASES"})
	@Produces({ MediaType.APPLICATION_JSON })
	public DBMSDTO getDBMSById(@PathParam("id") Long id) {
		return new DBMSDTO(dbmsController.findById(id));
	}
	
	@GET
	@Path("/search")
	@HasPermissions({"ADMIN_DATABASES"})
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DBMSDTO, DBMSBO> search(@QueryParam("start") int start, 
			@QueryParam("size") int size, @QueryParam("match") String match, @QueryParam("searchBy") String searchBy) {
		PaginatedBO<DBMSBO> paginatedBO = dbmsController.search(start, size, match, searchBy);
		return new PaginatedDTO<>(paginatedBO, DBMSDTO.class, DBMSBO.class);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@HasPermissions({"ADMIN_UO"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createDBMS(DBMSDTO dbmsDTO) {
		DBMSBO dbmsBO = dbmsDTO.toBO();
		dbmsController.createDBMS(dbmsBO);
		return Response.ok().build();
	}

	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@HasPermissions({"ADMIN_UO"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateDBMS(@PathParam("id") long id, DBMSDTO dbmsDTO) {
		DBMSBO dbmsBO = dbmsDTO.toBO(id);
		dbmsController.updateDBMS(dbmsBO);
		return Response.ok().build();
	}

	@Override
	@DELETE
	@Path("/{id}")
	@HasPermissions({"ADMIN_UO"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeDBMS(@PathParam("id") long id) {
		Response result = null;
		DBMSBO dbmsBO = new DBMSBO();
		dbmsBO.setId(id);
		try {
			dbmsController.removeDBMS(dbmsBO);
			result = Response.ok().build();
		}
		catch ( RuntimeException e ) {
			result = Response.serverError().build();
		}
		return result;
	}

}
