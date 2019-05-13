package es.juntadeandalucia.sigc.hc.core.controller;

import java.util.List;

import es.juntadeandalucia.sigc.hc.core.bo.PermissionBO;

public interface IPermissionController {
	
	public List<PermissionBO> getAllPermissions();
}
