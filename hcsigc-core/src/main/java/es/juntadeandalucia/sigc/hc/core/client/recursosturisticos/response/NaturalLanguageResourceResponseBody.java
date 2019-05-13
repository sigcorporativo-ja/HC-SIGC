package es.juntadeandalucia.sigc.hc.core.client.recursosturisticos.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NaturalLanguageResourceResponseBody  implements Serializable {

   private static final long serialVersionUID = -6444739775863395226L;

   @JsonProperty("numFound")
   private int numFound;

   @JsonProperty("start")
   private int start;

   @JsonProperty("docs")
   private List<NaturalLanguageResourceResponseDocs> docs = null;

   /**
    * No args constructor for use in serialization
    * 
    */
   public NaturalLanguageResourceResponseBody() {}

   /**
    * 
    * @param start
    * @param docs
    * @param numFound
    */
   public NaturalLanguageResourceResponseBody(int numFound, int start, List<NaturalLanguageResourceResponseDocs> docs) {
      super();
      this.numFound = numFound;
      this.start = start;
      this.docs = docs;
   }

   @JsonProperty("numFound")
   public int getNumFound() {
      return numFound;
   }

   @JsonProperty("numFound")
   public void setNumFound(int numFound) {
      this.numFound = numFound;
   }

   @JsonProperty("start")
   public int getStart() {
      return start;
   }

   @JsonProperty("start")
   public void setStart(int start) {
      this.start = start;
   }

   @JsonProperty("docs")
   public List<NaturalLanguageResourceResponseDocs> getDocs() {
      return docs;
   }

   @JsonProperty("docs")
   public void setDocs(List<NaturalLanguageResourceResponseDocs> docs) {
      this.docs = docs;
   }
}