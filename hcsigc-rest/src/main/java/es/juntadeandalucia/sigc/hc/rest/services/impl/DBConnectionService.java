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

import es.juntadeandalucia.sigc.hc.core.bo.DBConnectionBO;
import es.juntadeandalucia.sigc.hc.core.bo.DBMSBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.controller.IDBConnectionController;
import es.juntadeandalucia.sigc.hc.core.interceptor.binding.HasPermissions;
import es.juntadeandalucia.sigc.hc.rest.dto.DBConnectionCreationDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.DBConnectionDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;
import es.juntadeandalucia.sigc.hc.rest.services.IDBConnectionService;

@Path("/dbconnection")
public class DBConnectionService implements IDBConnectionService {

	@Inject
	private IDBConnectionController dbConnectionController;

	@Override
	@GET
	@Path("/")
	@HasPermissions({"ADMIN_DATABASES"})
	@Produces({ MediaType.APPLICATION_JSON })
	public List<DBConnectionDTO> getAllDBConnections() {
		return dbConnectionController.getAllDBConnections().stream().map(DBConnectionDTO::new).collect(Collectors.toList());
	}

	@Override
	@GET
	@Path("/paginate")
	@HasPermissions({"ADMIN_DATABASES"})
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DBConnectionDTO, DBConnectionBO> paginate(@QueryParam("start") int start, @QueryParam("size") int size) {
		PaginatedBO<DBConnectionBO> paginatedBO = dbConnectionController.paginate(start, size);
		return new PaginatedDTO<>(paginatedBO, DBConnectionDTO.class, DBConnectionBO.class);
	}

	@Override
	@GET
	@Path("/{id}")
	@HasPermissions({"ADMIN_DATABASES"})
	@Produces({ MediaType.APPLICATION_JSON })
	public DBConnectionDTO getDBConnectionById(@PathParam("id") Long id) {
		return new DBConnectionDTO(dbConnectionController.findById(id));
	}
	
	@GET
	@Path("/search")
	@HasPermissions({"ADMIN_DATABASES"})
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DBConnectionDTO, DBConnectionBO> search(@QueryParam("start") int start, 
			@QueryParam("size") int size, @QueryParam("match") String match, @QueryParam("searchBy") String searchBy) {
		PaginatedBO<DBConnectionBO> paginatedBO = dbConnectionController.search(start, size, match, searchBy);
		return new PaginatedDTO<>(paginatedBO, DBConnectionDTO.class, DBConnectionBO.class);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@HasPermissions({"ADMIN_DATABASES"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createDBConnection(DBConnectionCreationDTO dbConnectionCreationDTO) {
		DBMSBO dbms = dbConnectionCreationDTO.getDbms().toBO(dbConnectionCreationDTO.getDbms().getId());
		DBConnectionBO dbConnectionBO = dbConnectionCreationDTO.toBO();
		dbConnectionBO.setDbms(dbms);
		dbConnectionController.createDBConnection(dbConnectionBO);
		return Response.ok().build();
	}

	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@HasPermissions({"ADMIN_DATABASES"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateDBConnection(@PathParam("id") long id, DBConnectionCreationDTO dbConnectionCreationDTO) {
		DBMSBO dbms = dbConnectionCreationDTO.getDbms().toBO(dbConnectionCreationDTO.getDbms().getId());
		DBConnectionBO dbConnectionBO = dbConnectionCreationDTO.toBO(id);
		dbConnectionBO.setDbms(dbms);
		dbConnectionController.updateDBConnection(dbConnectionBO);
		return Response.ok().build();
	}

	@Override
	@DELETE
	@Path("/{id}")
	@HasPermissions({"ADMIN_DATABASES"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeDBConnection(@PathParam("id") long id) {
		Response result = null;
		DBConnectionBO dbConnectionBO = new DBConnectionBO();
		dbConnectionBO.setId(id);
		try {
			dbConnectionController.removeDBConnection(dbConnectionBO);
			result = Response.ok().build();
		}
		catch ( RuntimeException e ) {
			result = Response.serverError().build();
		}
		return result;
	}

}
