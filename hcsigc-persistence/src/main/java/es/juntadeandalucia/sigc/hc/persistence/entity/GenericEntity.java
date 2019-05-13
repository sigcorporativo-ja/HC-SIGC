package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;

public interface GenericEntity {

  Serializable getId();
  
  void setVersion(Long version);
  
  Long getVersion();
}
