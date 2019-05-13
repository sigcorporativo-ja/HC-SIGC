package es.juntadeandalucia.sigc.hc.rest.exceptionhandler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import es.juntadeandalucia.sigc.hc.core.interceptor.exception.UnauthorizedException;
import es.juntadeandalucia.sigc.hc.rest.dto.ErrorDTO;

@Provider
public class UnauthorizedExceptionHandler implements ExceptionMapper<UnauthorizedException> {

	@Override
	public Response toResponse(UnauthorizedException exception) {
		 return Response.serverError().entity(new ErrorDTO(Response.Status.UNAUTHORIZED, exception.getLocalizedMessage())).type(MediaType.APPLICATION_JSON).build();
	}

}
