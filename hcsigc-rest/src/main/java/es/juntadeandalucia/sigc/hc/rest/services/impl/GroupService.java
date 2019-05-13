package es.juntadeandalucia.sigc.hc.rest.services.impl;

import java.util.ArrayList;
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
import es.juntadeandalucia.sigc.hc.core.bo.GroupBO;
import es.juntadeandalucia.sigc.hc.core.bo.OrganizationUnitBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;
import es.juntadeandalucia.sigc.hc.core.bo.UserBO;
import es.juntadeandalucia.sigc.hc.core.controller.IGroupController;
import es.juntadeandalucia.sigc.hc.core.controller.IUserController;
import es.juntadeandalucia.sigc.hc.core.interceptor.binding.HasPermissions;
import es.juntadeandalucia.sigc.hc.rest.dto.GroupDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.PaginatedDTO;
import es.juntadeandalucia.sigc.hc.rest.dto.UserDTO;
import es.juntadeandalucia.sigc.hc.rest.services.IGroupService;

@Path("/groups")
public class GroupService implements IGroupService {

	@Inject
	private IGroupController groupController;
	@Inject
	private IUserController userController;

	@Override
	@GET
	@Path("/")
	@HasPermissions({"ADMIN_GROUP_USERS"})
	@Produces({ MediaType.APPLICATION_JSON })
	public List<GroupDTO> getAllGroup() {
		List<GroupDTO> groups = groupController.getAllGroup().stream().map(GroupDTO::new).collect(Collectors.toList());
		return groups;
	}

	@Override
	@GET
	@Path("/toshare")
	@HasPermissions({"SHARE_DATA"})
	@Produces({ MediaType.APPLICATION_JSON })
	public List<GroupDTO> getGroupsToShare() {
		List<GroupDTO> groups = groupController.getGroupsToShare().stream().map(GroupDTO::new).collect(Collectors.toList());
		return groups;
	}
	
	@Override
	@GET
	@Path("paginate")
	@HasPermissions({"ADMIN_GROUP_USERS"})
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<GroupDTO, GroupBO> paginate(@QueryParam("start") int start, @QueryParam("size") int size) {
		PaginatedBO<GroupBO> paginatedBO = groupController.paginate(start, size);
		return new PaginatedDTO<>(paginatedBO, GroupDTO.class, GroupBO.class);
	}

	@Override
	@GET
	@Path("{id}")
	@HasPermissions({"ADMIN_GROUP_USERS"})
	@Produces({ MediaType.APPLICATION_JSON })
	public GroupDTO getGroupById(@PathParam("id") Long id) {
		GroupDTO group = new GroupDTO(groupController.findById(id));

		// Inject the users in to groupDTO
		List<UserBO> usersBO = userController.usersFromGroupId(group.getId());
		List<UserDTO> usersDTO = usersBO.stream().map(UserDTO::new).collect(Collectors.toList());

		group.setUsers(usersDTO);

		return group;
	}

	@GET
	@Path("search")
	@HasPermissions({"ADMIN_GROUP_USERS"})
	@Produces({ MediaType.APPLICATION_JSON })
	public PaginatedDTO<GroupDTO, GroupBO> search(@QueryParam("start") int start,
			@QueryParam("size") int size, @QueryParam("match") String match, @QueryParam("searchBy") String searchBy) {
		PaginatedBO<GroupBO> paginatedBO = groupController.search(start, size, match, searchBy);
		return new PaginatedDTO<>(paginatedBO, GroupDTO.class, GroupBO.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@HasPermissions({"ADMIN_GROUP"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createGroup(GroupDTO groupDTO) {
		// get usersBO from dto
		List<UserBO> users = groupDTO.getUsers().stream().map(UserDTO::toBO).collect(Collectors.toList());
		List<DBConnectionBO> dbconnections = new ArrayList<>();
		groupDTO.getDbconnections().stream().forEach(db -> {
			dbconnections.add(db.toBO(db.getId()));
		});
		OrganizationUnitBO ou = groupDTO.getOu().toBO(groupDTO.getOu().getId());
		GroupBO groupBO = groupDTO.toBO();
		groupBO.setOrganization(ou);
		groupBO.setUsers(users);
		groupBO.setDbconnections(dbconnections);

		groupController.createGroup(groupBO);

		return Response.ok().build();
	}

	@Override
	@DELETE
	@Path("/{id}")
	@HasPermissions({"ADMIN_GROUP"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeGroup(@PathParam("id") long id) {
		GroupBO groupBO = groupController.findById(id);
		groupController.removeGroup(groupBO);
		return Response.ok().build();
	}

	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@HasPermissions({"ADMIN_GROUP_USERS"})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateGroup(@PathParam("id") long id, GroupDTO groupDTO) {
		// get usersBO from dto
		List<UserBO> usersBO = groupDTO.getUsers().stream().map(UserDTO::toBO).collect(Collectors.toList());
		List<DBConnectionBO> dbconnections = new ArrayList<>();
		groupDTO.getDbconnections().stream().forEach(db -> {
			dbconnections.add(db.toBO(db.getId()));
		});	
		OrganizationUnitBO ou = groupDTO.getOu().toBO(groupDTO.getOu().getId());
		GroupBO groupBO = groupDTO.toBO(id);
		groupBO.setOrganization(ou);
		groupBO.setUsers(usersBO);
		groupBO.setDbconnections(dbconnections);

		groupController.updateGroup(groupBO);
		return Response.ok().build();
	}

}
