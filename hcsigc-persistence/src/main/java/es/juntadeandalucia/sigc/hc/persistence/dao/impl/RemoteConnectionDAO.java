package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import javax.ejb.Stateless;

import es.juntadeandalucia.sigc.hc.persistence.dao.IRemoteConnectionDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.RemoteConnection;

@Stateless
public class RemoteConnectionDAO extends GenericDAO<RemoteConnection, Integer> implements IRemoteConnectionDAO {

	private static final long serialVersionUID = -2731597923563342453L;

}
