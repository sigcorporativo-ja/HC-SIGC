package es.juntadeandalucia.sigc.hc.core.client.recursosturisticos.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NaturalLanguageResourceRequest implements Serializable {

   private static final long serialVersionUID = -1632571807809608884L;

   @JsonProperty("query")
   private String query;

   @JsonProperty("query_filter")
   private String queryFilter;

   @JsonProperty("page_size")
   private int pageSize;

   @JsonProperty("item_number")
   private int itemNumber;

   /**
    * No args constructor for use in serialization
    * 
    */
   public NaturalLanguageResourceRequest() {}

   /**
    * 
    * @param queryFilter
    * @param query
    * @param pageSize
    * @param itemNumber
    */
   public NaturalLanguageResourceRequest(String query, String queryFilter, int pageSize, int itemNumber) {
      this.query = query;
      this.queryFilter = queryFilter;
      this.pageSize = pageSize;
      this.itemNumber = itemNumber;
   }

   public String getQuery() {
      return query;
   }

   public void setQuery(String query) {
      this.query = query;
   }

   public String getQueryFilter() {
      return queryFilter;
   }

   public void setQueryFilter(String queryFilter) {
      this.queryFilter = queryFilter;
   }

   public int getPageSize() {
      return pageSize;
   }

   public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
   }

   public int getItemNumber() {
      return itemNumber;
   }

   public void setItemNumber(int itemNumber) {
      this.itemNumber = itemNumber;
   }
}
