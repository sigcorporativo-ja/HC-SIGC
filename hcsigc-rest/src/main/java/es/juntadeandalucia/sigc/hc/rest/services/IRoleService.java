package es.juntadeandalucia.sigc.hc.rest.services;

import java.util.List;
import java.util.stream.Collectors;

import es.juntadeandalucia.sigc.hc.rest.dto.RoleDTO;

public interface IRoleService {

	public List<RoleDTO> getAllRoles();

	public RoleDTO getRoleById(Integer id);
}
