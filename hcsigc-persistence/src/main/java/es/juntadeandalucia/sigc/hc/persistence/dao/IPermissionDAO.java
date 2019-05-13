package es.juntadeandalucia.sigc.hc.persistence.dao;

import es.juntadeandalucia.sigc.hc.persistence.entity.Permission;

public interface IPermissionDAO extends IGenericDAO<Permission, Long> {
	public Permission findById(Long id);
}
