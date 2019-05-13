package es.juntadeandalucia.sigc.hc.rest;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

/**
 * Se usará esta clase si el servicio REST se incluye mediante una dependencia.
 * En caso de incluirse directamente en el proyecto se usará ArchetypeRestWADLListener
 *
 */
public class RestWADLInitializer implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {

		Dynamic wadlServlet = servletContext.addServlet("rest.wadl", org.jboss.resteasy.wadl.ResteasyWadlServlet.class);
		if (null != wadlServlet) {
			wadlServlet.addMapping("/api/services.wadl");
		}
	}

}
