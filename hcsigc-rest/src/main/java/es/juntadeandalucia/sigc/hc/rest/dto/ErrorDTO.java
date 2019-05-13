package es.juntadeandalucia.sigc.hc.rest.dto;

import javax.ws.rs.core.Response.Status;

public class ErrorDTO {

   private Integer status;
   private String message;

   public ErrorDTO(Status status, String message) {
		this(status.getStatusCode(), message);
   }
   
   public ErrorDTO(int code, String message) {
	      this.status = code;
	      this.message = message;
   }

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
      this.status = status;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }
}
