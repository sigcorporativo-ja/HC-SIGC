package es.juntadeandalucia.sigc.hc.rest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.annotation.WebListener;

/**
 * Se usará esta clase si el servicio REST está directamente en el proyecto.
 * En caso de incluirse mediante una dependencia se usará ArchetypeRestWADLInitializer
 *
 */
@WebListener
public class RestWADLListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {

		Dynamic wadlServlet = event.getServletContext().addServlet("rest.wadl",
				org.jboss.resteasy.wadl.ResteasyWadlServlet.class);
		if (wadlServlet != null) {
			wadlServlet.addMapping("/api/services.wadl");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// Do stuff during server shutdown.
	}

}