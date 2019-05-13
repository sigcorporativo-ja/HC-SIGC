package es.juntadeandalucia.sigc.hc.core.controller.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.juntadeandalucia.sigc.hc.core.bo.PermissionBO;
import es.juntadeandalucia.sigc.hc.core.controller.IPermissionController;
import es.juntadeandalucia.sigc.hc.persistence.dao.IPermissionDAO;

@Transactional
public class PermissionController implements IPermissionController {
	
	private static final Log LOG = LogFactory.getLog(PermissionController.class);

	@Inject
	private IPermissionDAO configurationDao;
	
	@Override
	public List<PermissionBO> getAllPermissions() {
		List<PermissionBO> result = configurationDao.findAll().stream().map(PermissionBO::new).collect(Collectors.toList());
		LOG.info("List of Permissions");
		return result;
	}

}
