package es.juntadeandalucia.sigc.hc.rest.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Provider
@PreMatching
public class ResponseFilter implements javax.ws.rs.container.ContainerResponseFilter {

	private static final Log LOG = LogFactory.getLog(ResponseFilter.class);

	@Override
	public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {

//		LOG.debug("Filtering REST Response");
//
//		// Maybe limit certain domains with Access-Control-Allow-Origin instead of '*'
//		responseCtx.getHeaders().remove("Access-Control-Allow-Origin");
//		responseCtx.getHeaders().add("Access-Control-Allow-Origin", "*");
//		responseCtx.getHeaders().add("Access-Control-Allow-Credentials", "true");
//		responseCtx.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
		responseCtx.getHeaders().add("Access-Control-Allow-Headers", HttpHeaders.ACCEPT + ","
				+ HttpHeaders.AUTHORIZATION + "," + HttpHeaders.CACHE_CONTROL + "," + HttpHeaders.CONTENT_TYPE);
	}
}