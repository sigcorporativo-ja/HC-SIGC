package es.juntadeandalucia.sigc.hc.rest.exceptionhandler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import es.juntadeandalucia.sigc.hc.core.interceptor.exception.NotLoggedInException;
import es.juntadeandalucia.sigc.hc.rest.dto.ErrorDTO;

public class NotLoggedInExceptionHandler implements ExceptionMapper<NotLoggedInException> {

   @Override
   public Response toResponse(NotLoggedInException exception) {
      return Response.serverError().entity(new ErrorDTO(401, exception.getLocalizedMessage())).type(MediaType.APPLICATION_JSON).build();
   }

}
