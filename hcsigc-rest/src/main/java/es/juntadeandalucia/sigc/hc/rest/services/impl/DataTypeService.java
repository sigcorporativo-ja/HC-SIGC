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

import es.juntadeandalucia.sigc.hc.core.bo.DataTypeBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.controller.IDataTypeController;
import es.juntadeandalucia.sigc.hc.rest.dto.DataTypeDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;
import es.juntadeandalucia.sigc.hc.rest.services.IDataTypeService;

@Path("/datatype")
public class DataTypeService implements IDataTypeService {

	@Inject
	private IDataTypeController dataTypeController;
	
	@Override
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<DataTypeDTO> getAllDataType() {
		return dataTypeController.getAllDataType().stream().map(DataTypeDTO::new).collect(Collectors.toList());
	}

	@Override
	@GET
	@Path("/paginate")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DataTypeDTO, DataTypeBO> paginate(@QueryParam("start") int start, @QueryParam("size") int size) {
		PaginatedBO<DataTypeBO> paginatedBO = dataTypeController.paginate(start, size);
		return new PaginatedDTO<>(paginatedBO, DataTypeDTO.class, DataTypeBO.class);
	}

	@Override
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DataTypeDTO getDataTypeById(@PathParam("id") Long id) {
		return new DataTypeDTO(dataTypeController.findById(id));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createDataType(DataTypeDTO dataTypeDTO) {
		DataTypeBO dataTypeBO = dataTypeDTO.toBO();
		dataTypeController.createDataType(dataTypeBO);
		return Response.ok().build();
	}

	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateDataType(@PathParam("id") long id, DataTypeDTO dataTypeDTO) {
		DataTypeBO dataTypeBO = dataTypeDTO.toBO(id);
		dataTypeController.updateDataType(dataTypeBO);
		return Response.ok().build();
	}

	@Override
	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeDataType(@PathParam("id") long id) {
		DataTypeBO dataTypeBO = new DataTypeBO();
		dataTypeBO.setId(id);
		dataTypeController.removeDataType(dataTypeBO);
		return Response.ok().build();
	}
	
}
