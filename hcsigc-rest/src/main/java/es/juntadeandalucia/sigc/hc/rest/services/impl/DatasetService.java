package es.juntadeandalucia.sigc.hc.rest.services.impl;

import java.io.IOException;
import java.io.InputStream;
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

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import es.juntadeandalucia.sigc.hc.core.bo.DatasetBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.controller.IDatasetController;
import es.juntadeandalucia.sigc.hc.core.interceptor.binding.HasPermissions;
import es.juntadeandalucia.sigc.hc.core.file.IData;
import es.juntadeandalucia.sigc.hc.core.file.impl.CSVData;
import es.juntadeandalucia.sigc.hc.core.file.impl.GMLData;
import es.juntadeandalucia.sigc.hc.core.file.impl.GPXData;
import es.juntadeandalucia.sigc.hc.core.file.impl.GeoJSONData;
import es.juntadeandalucia.sigc.hc.core.file.impl.KMLData;
import es.juntadeandalucia.sigc.hc.core.file.impl.ShapeData;
import es.juntadeandalucia.sigc.hc.core.file.impl.TextData;
import es.juntadeandalucia.sigc.hc.rest.dto.DatasetDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;
import es.juntadeandalucia.sigc.hc.rest.services.IDatasetService;

@Path("/datasets")
public class DatasetService implements IDatasetService {

	private static final Log LOG = LogFactory.getLog(DatasetService.class);

	@Inject
	private IDatasetController datasetController;

	@Override
	@GET
	@Path("/")
	@HasPermissions({ "VIEW_DATA" })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<DatasetDTO> getAllDatasets() {
		return datasetController.getAllDatasets().stream().map(DatasetDTO::new).collect(Collectors.toList());
	}

	@Override
	@GET
	@Path("/paginate")
	@HasPermissions({ "VIEW_DATA" })
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DatasetDTO, DatasetBO> paginate(@QueryParam("start") int start, @QueryParam("size") int size,
			@QueryParam("checks") String checks, @QueryParam("sorts") String sorts,
			@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
		PaginatedBO<DatasetBO> paginatedBO = datasetController.paginate(start, size, checks, sorts, startDate, endDate);
		return new PaginatedDTO<>(paginatedBO, DatasetDTO.class, DatasetBO.class);
	}

	@Override
	@GET
	@Path("/{id}")
	@HasPermissions({ "VIEW_DATA" })
	@Produces({ MediaType.APPLICATION_JSON })
	public DatasetDTO getDatasetById(@PathParam("id") Long id) {
		return new DatasetDTO(datasetController.findById(id));
	}

	@GET
	@Path("/search")
	@HasPermissions({ "VIEW_DATA" })
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<DatasetDTO, DatasetBO> search(@QueryParam("start") int start, @QueryParam("size") int size,
			@QueryParam("match") String match, @QueryParam("searchBy") String searchBy,
			@QueryParam("checks") String checks, @QueryParam("sorts") String sorts,
			@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
		PaginatedBO<DatasetBO> paginatedBO = datasetController.search(start, size, match, searchBy, checks, sorts,
				startDate, endDate);
		return new PaginatedDTO<>(paginatedBO, DatasetDTO.class, DatasetBO.class);
	}

	@PUT
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/upload")
	@HasPermissions({ "SHARE_DATA" })
	public Response uploadFile(MultipartFormDataInput multipart) {
		Response response;
		try {
			String name = multipart.getFormDataPart("name", String.class, null);
			String description = multipart.getFormDataPart("description", String.class, null);
			Boolean global = multipart.getFormDataPart("global", Boolean.class, null);
			InputStream fileInputStream = multipart.getFormDataPart("file", InputStream.class, null);
			String fileName = multipart.getFormDataPart("fileName", String.class, null);
			IData<? extends Iterable<String>> data = createData(fileInputStream, fileName);

			DatasetBO datasetBO = new DatasetBO();
			datasetBO.setName(name);
			datasetBO.setDescription(description);
			datasetBO.setGlobal(global);

			Long dataUploadId = datasetController.uploadData(datasetBO, data);
			Long datasetId = datasetController.createDataset(datasetBO);

			response = Response.ok("dataUploadId=" + dataUploadId + ",datasetId=" + datasetId).build();
		} catch (IOException e) {
			LOG.error("Error creating dataset: " + e);
			response = Response.serverError().build();
		}

		return response;
	}

	private IData<? extends Iterable<String>> createData(InputStream fileInputStream, String fileName) {
		IData<List<String>> data;

		String fileExt = FilenameUtils.getExtension(fileName).toUpperCase();
		if ("CSV".equalsIgnoreCase(fileExt)) {
			data = new CSVData(fileInputStream);
		} else if ("TXT".equalsIgnoreCase(fileExt)) {
			data = new TextData(fileInputStream);
		} else if ("JSON".equalsIgnoreCase(fileExt)) {
			// TODO ver que pasa con tipojson
//				data = new TopoJSONData(fileInputStream);
			data = new GeoJSONData(fileInputStream);
		} else if ("XML".equalsIgnoreCase(fileExt)) {
			data = new GMLData(fileInputStream);
		} else if ("KML".equalsIgnoreCase(fileExt)) {
			data = new KMLData(fileInputStream);
		} else if ("GPX".equalsIgnoreCase(fileExt)) {
			data = new GPXData(fileInputStream);
		} else if ("ZIP".equalsIgnoreCase(fileExt)) {
			data = new ShapeData(fileInputStream);
		} else {
			data = null;
		}
		return data;
	}

	//TODO actualmente no es usado, se sustituye por uploadFile()
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@HasPermissions({ "SHARE_DATA" })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createDataset(DatasetDTO datasetDTO) {
		DatasetBO datasetBO = datasetDTO.toBO();
		datasetController.createDataset(datasetBO);
		return Response.ok().build();
	}

	@Override
	@DELETE
	@Path("/{id}")
	@HasPermissions({ "SHARE_DATA" })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeDataset(@PathParam("id") long id) {
		DatasetBO datasetBO = new DatasetBO();
		datasetBO.setId(id);
		datasetController.removeDataset(datasetBO);
		return Response.ok().build();
	}
	
	@Override
	@DELETE
	@Path("/batchDataUpload/{id}")
	@HasPermissions({ "SHARE_DATA" })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeBatchDataUpload(@PathParam("id") long id) {
		datasetController.removeBatchDataUpload(id);
		return Response.ok().build();
	}
	
	@Override
	@DELETE
	@Path("/upload/{id}")
	@HasPermissions({ "SHARE_DATA" })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeDataUpload(@PathParam("id") long id) {
		datasetController.removeDataUpload(id);
		return Response.ok().build();
	}

	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@HasPermissions({ "SHARE_DATA" })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateDataset(@PathParam("id") long id, DatasetDTO datasetDTO) {
		DatasetBO datasetBO = datasetDTO.toBO(id);
		datasetController.updateDataset(datasetBO);
		return Response.ok().build();
	}

}
