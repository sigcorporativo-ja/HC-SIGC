package es.juntadeandalucia.sigc.hc.core.client.recursosturisticos.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NaturalLanguageResourceResponseDocs  implements Serializable {
   private static final long serialVersionUID = -7557759909012358611L;

   @JsonProperty("name")
   private String name;

   @JsonProperty("suggestion")
   private List<String> suggestion = null;

   @JsonProperty("y_coord")
   private double yCoord;

   @JsonProperty("image")
   private String image;

   @JsonProperty("x_coord")
   private double xCoord;

   @JsonProperty("id")
   private long id;

   @JsonProperty("resource_type.code")
   private String resourceTypeCode;

   @JsonProperty("resource_type.name")
   private String resourceTypeName;

   @JsonIgnore
   @JsonProperty("_version_")
   private long version;

   /**
    * No args constructor for use in serialization
    * 
    */
   public NaturalLanguageResourceResponseDocs() {}

   /**
    * 
    * @param id
    * @param resourceTypeName
    * @param xCoord
    * @param name
    * @param image
    * @param yCoord
    * @param suggestion
    * @param version
    * @param resourceTypeCode
    */
   public NaturalLanguageResourceResponseDocs(String name, List<String> suggestion, double yCoord, String image, double xCoord, int id,
         String resourceTypeCode, String resourceTypeName, int version) {
      super();
      this.name = name;
      this.suggestion = suggestion;
      this.yCoord = yCoord;
      this.image = image;
      this.xCoord = xCoord;
      this.id = id;
      this.resourceTypeCode = resourceTypeCode;
      this.resourceTypeName = resourceTypeName;
      this.version = version;
   }

   @JsonProperty("name")
   public String getName() {
      return name;
   }

   @JsonProperty("name")
   public void setName(String name) {
      this.name = name;
   }

   @JsonProperty("suggestion")
   public List<String> getSuggestion() {
      return suggestion;
   }

   @JsonProperty("suggestion")
   public void setSuggestion(List<String> suggestion) {
      this.suggestion = suggestion;
   }

   @JsonProperty("y_coord")
   public double getYCoord() {
      return yCoord;
   }

   @JsonProperty("y_coord")
   public void setYCoord(double yCoord) {
      this.yCoord = yCoord;
   }

   @JsonProperty("image")
   public String getImage() {
      return image;
   }

   @JsonProperty("image")
   public void setImage(String image) {
      this.image = image;
   }

   @JsonProperty("x_coord")
   public double getXCoord() {
      return xCoord;
   }

   @JsonProperty("x_coord")
   public void setXCoord(double xCoord) {
      this.xCoord = xCoord;
   }

   @JsonProperty("id")
   public long getId() {
      return id;
   }

   @JsonProperty("id")
   public void setId(long id) {
      this.id = id;
   }

   @JsonProperty("resource_type.code")
   public String getResourceTypeCode() {
      return resourceTypeCode;
   }

   @JsonProperty("resource_type.code")
   public void setResourceTypeCode(String resourceTypeCode) {
      this.resourceTypeCode = resourceTypeCode;
   }

   @JsonProperty("resource_type.name")
   public String getResourceTypeName() {
      return resourceTypeName;
   }

   @JsonProperty("resource_type.name")
   public void setResourceTypeName(String resourceTypeName) {
      this.resourceTypeName = resourceTypeName;
   }

   @JsonProperty("_version_")
   public long getVersion() {
      return version;
   }

   @JsonProperty("_version_")
   public void setVersion(long version) {
      this.version = version;
   }

}