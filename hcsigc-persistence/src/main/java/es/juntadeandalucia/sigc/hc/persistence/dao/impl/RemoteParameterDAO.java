package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import javax.ejb.Stateless;

import es.juntadeandalucia.sigc.hc.persistence.dao.IRemoteParameterDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.RemoteParameter;

@Stateless
public class RemoteParameterDAO extends GenericDAO<RemoteParameter, Integer> implements IRemoteParameterDAO {

	private static final long serialVersionUID = -306114029405811601L;

}
