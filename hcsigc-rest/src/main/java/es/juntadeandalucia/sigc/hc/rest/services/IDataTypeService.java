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

import es.juntadeandalucia.sigc.hc.core.bo.DataTypeBO;
import es.juntadeandalucia.sigc.hc.rest.dto.DataTypeDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;

public interface IDataTypeService {

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<DataTypeDTO> getAllDataType();

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DataTypeDTO, DataTypeBO> paginate(@QueryParam("start") int start, @QueryParam("size") int size);

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DataTypeDTO getDataTypeById(@PathParam("id") Long id);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createDataType(DataTypeDTO dataTypeDTO);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateDataType(@PathParam("id") long id, DataTypeDTO dataTypeDTO);

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeDataType(@PathParam("id") long id);
}
