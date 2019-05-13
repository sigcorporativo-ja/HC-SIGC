package es.juntadeandalucia.sigc.hc.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class HCSIGCRestApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public HCSIGCRestApplication() {
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
