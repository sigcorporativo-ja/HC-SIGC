package es.juntadeandalucia.sigc.hc.rest.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
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
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.controller.IConfigurationController;
import es.juntadeandalucia.sigc.hc.core.interceptor.binding.HasPermissions;
import es.juntadeandalucia.sigc.hc.rest.dto.ConfigurationDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;
import es.juntadeandalucia.sigc.hc.rest.services.IConfigurationService;

@Path("/configuration")
public class ConfigurationService implements IConfigurationService {

	@Inject
	private IConfigurationController configurationController;

	@Override
	@GET
	@Path("/")
	@HasPermissions({"ADMIN_GROUP"})
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ConfigurationDTO> getAllConfiguration() {
		return configurationController.getAllConfiguration().stream().map(ConfigurationDTO::new).collect(Collectors.toList());
	}

	@Override
	@GET
	@Path("/paginate")
	@HasPermissions({"ADMIN_CONFIG"})
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<ConfigurationDTO, ConfigurationBO> paginate(@QueryParam("start") int start, @QueryParam("size") int size) {
		PaginatedBO<ConfigurationBO> paginatedBO = configurationController.paginate(start, size);
		return new PaginatedDTO<>(paginatedBO, ConfigurationDTO.class, ConfigurationBO.class);
	}

	@Override
	@GET
	@Path("/{id}")
	@HasPermissions({"ADMIN_CONFIG"})
	@Produces({ MediaType.APPLICATION_JSON })
	public ConfigurationDTO getConfigurationById(@PathParam("id") Long id) {
		return new ConfigurationDTO(configurationController.findById(id));
	}
	
	@GET
	@Path("/search")
	@HasPermissions({"ADMIN_CONFIG"})
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<ConfigurationDTO, ConfigurationBO> search(@QueryParam("start") int start, 
			@QueryParam("size") int size, @QueryParam("match") String match, @QueryParam("searchBy") String searchBy) {
		PaginatedBO<ConfigurationBO> paginatedBO = configurationController.search(start, size, match, searchBy);
		return new PaginatedDTO<>(paginatedBO, ConfigurationDTO.class, ConfigurationBO.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@HasPermissions({"ADMIN_CONFIG"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createConfiguration(ConfigurationDTO configurationDTO) {
		ConfigurationBO configurationBO = configurationDTO.toBO();
		configurationController.createConfiguration(configurationBO);
		return Response.ok().build();
	}

	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@HasPermissions({"ADMIN_CONFIG"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateConfiguration(@PathParam("id") long id, ConfigurationDTO configurationDTO) {
		ConfigurationBO configurationBO = configurationDTO.toBO(id);
		configurationController.updateConfiguration(configurationBO);
		return Response.ok().build();
	}

}
