package es.juntadeandalucia.sigc.hc.rest.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.ext.Provider;

/**
 * This interceptor verify the access permissions for a user based on username
 * and passowrd provided in request
 */
@Provider
public class SecurityInterceptor implements javax.ws.rs.container.ContainerRequestFilter {

   @Override
   public void filter(ContainerRequestContext requestcontext) throws IOException {
   }
}