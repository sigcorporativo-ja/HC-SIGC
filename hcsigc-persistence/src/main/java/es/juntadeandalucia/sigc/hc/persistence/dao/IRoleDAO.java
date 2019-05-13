package es.juntadeandalucia.sigc.hc.persistence.dao;

import java.util.List;

import es.juntadeandalucia.sigc.hc.persistence.entity.Role;

public interface IRoleDAO extends IGenericDAO<Role, Integer> {
	Role findById(Integer id);
	List<Role> findAllEager();
}
