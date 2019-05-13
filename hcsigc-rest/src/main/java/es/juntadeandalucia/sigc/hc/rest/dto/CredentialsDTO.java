package es.juntadeandalucia.sigc.hc.rest.dto;

import java.io.Serializable;

public class CredentialsDTO implements Serializable {
   private static final long serialVersionUID = 1534490807166344098L;
   
   private String username;
   private String password;

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
