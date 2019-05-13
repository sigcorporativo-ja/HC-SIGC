package es.juntadeandalucia.sigc.hc.core.bo;

import es.juntadeandalucia.sigc.hc.persistence.entity.RemoteConnection;

public class RemoteConnectionBO extends GenericBO<RemoteConnection> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1730323139262462514L;
	
	public RemoteConnectionBO(RemoteConnection remoteConn) {
		this(remoteConn, false);
	}
	
	public RemoteConnectionBO(RemoteConnection remoteConn, boolean eager) {
		super(remoteConn, eager);
	}

	@Override
	protected void initializeFromEntity(RemoteConnection remoteConn, boolean eager) {
		// TODO Auto-generated method stub
	}

}
