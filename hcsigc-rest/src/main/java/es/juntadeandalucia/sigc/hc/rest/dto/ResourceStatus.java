package es.juntadeandalucia.sigc.hc.rest.dto;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Status of a resource request
 * 
 * @author Guadaltel S.A.
 *
 */
public enum ResourceStatus {

   PUBLICADO("Publicado"),
   PENDIENTE("Pendiente"),
   CERRADO("Cerrado"),
   SOLICITADO("Solicitado");
   
   private String name;
   
   private ResourceStatus(String name) {
      this.name= name;
   }

   @JsonValue
   public String getName() {
       return name;
   }
}
