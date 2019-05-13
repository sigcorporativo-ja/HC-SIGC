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

import es.juntadeandalucia.sigc.hc.core.bo.DatasetBO;
import es.juntadeandalucia.sigc.hc.rest.dto.DatasetDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;

public interface IDatasetService {

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<DatasetDTO> getAllDatasets();

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DatasetDTO, DatasetBO> paginate(@QueryParam("start") int start, @QueryParam("size") int size,
			@QueryParam("checks") String checks, @QueryParam("sorts") String sorts ,@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate);

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DatasetDTO getDatasetById(@PathParam("id") Long id);

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DatasetDTO, DatasetBO> search(@QueryParam("start") int start, @QueryParam("size") int size,
			@QueryParam("match") String match, @QueryParam("searchBy") String searchBy,
			@QueryParam("checks") String checks, @QueryParam("sorts") String sorts , @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createDataset(DatasetDTO datasetDTO);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateDataset(@PathParam("id") long id, DatasetDTO datasetDTO);

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeDataset(@PathParam("id") long id);

	@DELETE
	@Path("/batchDataUpload/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeBatchDataUpload(@PathParam("id")long id);
	
	@DELETE
	@Path("/upload/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeDataUpload(@PathParam("id") long id);

}
