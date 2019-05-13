
package es.juntadeandalucia.sigc.hc.core.client.recursosturisticos.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NaturalLanguageResourceResponse implements Serializable {

   private static final long serialVersionUID = 76593244639126625L;

   @JsonIgnore
   @JsonProperty("responseHeader")
   private Object responseHeader = null;

   @JsonProperty("response")
   private NaturalLanguageResourceResponseBody response;

   /**
    * No args constructor for use in serialization
    * 
    */
   public NaturalLanguageResourceResponse() {}

   /**
    * 
    * @param response
    * @param responseHeader
    */
   public NaturalLanguageResourceResponse(Object responseHeader, NaturalLanguageResourceResponseBody response) {
      this.response = response;
   }

   @JsonProperty("response")
   public NaturalLanguageResourceResponseBody getResponse() {
      return response;
   }

   @JsonProperty("response")
   public void setResponse(NaturalLanguageResourceResponseBody response) {
      this.response = response;
   }
}