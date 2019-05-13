package es.juntadeandalucia.sigc.hc.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.GenericBO;

/**
 * This is the base class for all defined DTOs. It has de common attributes and
 * functions.
 * 
 * @author Guadaltel S.A.
 *
 */
public abstract class GenericDTO<T extends GenericBO<?>> {
   
   /**
    * Id of the DTO
    */
   @JsonProperty("id")
   protected Long id;
   
   /**
    * Default constructor
    */
   public GenericDTO() {}
   
   /**
    * Generic constructor for a DTO
    * @param id
    */
   public GenericDTO(Long id) {
      this.id = id;
   }
   
   /**
    * Constructor by a BO
    * @param bo
    */
   public GenericDTO(T bo) {
	   this.initalizeFromBO(bo);
   }
   
   public Long getId() {
	return id;
   }

	public void setId(Long id) {
		this.id = id;
	}

	protected abstract void initalizeFromBO(T bo);

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      boolean equals = false;
      if (getId() != null && obj != null && obj instanceof GenericDTO) {
         @SuppressWarnings("unchecked")
		GenericDTO<T> genericDto = (GenericDTO<T>) obj;
         equals = getId().equals(genericDto.getId());
      }
      return equals;
   }
   
}
