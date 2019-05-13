package es.juntadeandalucia.sigc.hc.rest.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.juntadeandalucia.sigc.hc.core.bo.ConfigurationBO;
import es.juntadeandalucia.sigc.hc.rest.dto.ConfigurationDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;

public interface IConfigurationService {

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ConfigurationDTO> getAllConfiguration();
	
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<ConfigurationDTO, ConfigurationBO> paginate(@QueryParam("start") int start,
			@QueryParam("size") int size);

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<ConfigurationDTO, ConfigurationBO> search(@QueryParam("start") int start, @QueryParam("size") int size, @QueryParam("match") String match, @QueryParam("searchBy") String searchBy);
	
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ConfigurationDTO getConfigurationById(@PathParam("id") Long id);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createConfiguration(ConfigurationDTO configurationDTO);
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateConfiguration(@PathParam("id") long id, ConfigurationDTO configurationDTO);
}